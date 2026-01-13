package com.via.runnable.batch.common.job;

import com.via.common.app.career.dto.CreateUniversity;
import com.via.common.crawler.dto.CrawledUniversity;
import com.via.runnable.batch.common.partitioner.UniversityPartitioner;
import com.via.runnable.batch.common.processor.CreateUniversityProcessor;
import com.via.runnable.batch.common.reader.UniversityClientReader;
import com.via.runnable.batch.common.writer.UniversityWriter;
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
public class UpdateUniversityConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final UniversityClientReader universityClientReader;
    private final UniversityWriter universityWriter;
    private final UniversityPartitioner universityPartitioner;
    private final CreateUniversityProcessor createUniversityProcessor;

    private final int GRID_SIZE = 5;

    @Bean
    public Job updateUniversity() {
        return new JobBuilder("updateUniversity", jobRepository)
                .start(updateUniversityMasterStep())
                .build();
    }

    @Bean
    public Step updateUniversityMasterStep() {
        return new StepBuilder("updateUniversityMasterStep", jobRepository)
                .partitioner("updateUniversityWorkerStep", universityPartitioner)
                .step(updateUniversityWorkerStep())
                .gridSize(GRID_SIZE)
                .taskExecutor(universityTaskExecutor())
                .build();
    }

    @Bean
    public Step updateUniversityWorkerStep() {
        return new StepBuilder("updateUniversityWorkerStep", jobRepository)
                .<CrawledUniversity, CreateUniversity>chunk(1000, transactionManager)
                .reader(universityClientReader)
                .processor(createUniversityProcessor)
                .writer(universityWriter)
                .build();
    }

    @Bean
    public TaskExecutor universityTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(GRID_SIZE);
        executor.setMaxPoolSize(GRID_SIZE);
        executor.setQueueCapacity(GRID_SIZE);
        executor.setThreadNamePrefix("university-batch-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}
