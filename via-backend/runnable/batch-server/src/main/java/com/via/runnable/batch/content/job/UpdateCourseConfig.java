package com.via.runnable.batch.content.job;

import com.via.content.app.course.dto.CreateCourse;
import com.via.content.crawler.dto.CrawledCourse;
import com.via.content.domain.course.enums.CoursePlatform;
import com.via.runnable.batch.content.partitioner.CoursePlatformPartitioner;
import com.via.runnable.batch.content.processor.CreateCourseProcessor;
import com.via.runnable.batch.content.reader.CourseCrawlerReader;
import com.via.runnable.batch.content.writer.CourseWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class UpdateCourseConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final CourseCrawlerReader courseCrawlerReader;
    private final CoursePlatformPartitioner coursePlatformPartitioner;
    private final CourseWriter courseWriter;
    private final CreateCourseProcessor createCourseProcessor;

    private final int GRID_SIZE = CoursePlatform.values().length;

    @Bean
    public Job updateCourse() {
        return new JobBuilder("updateCourse", jobRepository)
                .start(updateCourseMasterStep())
                .build();
    }

    @Bean
    public Step updateCourseMasterStep() {
        return new StepBuilder("updateCourseMasterStep", jobRepository)
                .partitioner("updateCourseWorkerStep", coursePlatformPartitioner)
                .step(updateCourseWorkerStep())
                .gridSize(GRID_SIZE)
                .taskExecutor(courseTaskExecutor())
                .build();
    }

    @Bean
    public Step updateCourseWorkerStep() {
        return new StepBuilder("updateCourseWorkerStep", jobRepository)
                .<CrawledCourse, CreateCourse>chunk(500, transactionManager)
                .reader(courseCrawlerReader)
                .processor(createCourseProcessor)
                .writer(courseWriter)
                .build();
    }

    @Bean
    public TaskExecutor courseTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(GRID_SIZE);
        executor.setMaxPoolSize(GRID_SIZE);
        executor.setQueueCapacity(GRID_SIZE);
        executor.setThreadNamePrefix("course-batch-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}