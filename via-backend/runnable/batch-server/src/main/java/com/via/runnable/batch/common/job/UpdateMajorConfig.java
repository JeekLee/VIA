package com.via.runnable.batch.common.job;

import com.via.common.app.career.dto.CreateMajor;
import com.via.common.crawler.dto.CrawledMajor;
import com.via.runnable.batch.common.partitioner.MajorPartitioner;
import com.via.runnable.batch.common.processor.CreateMajorProcessor;
import com.via.runnable.batch.common.reader.MajorClientReader;
import com.via.runnable.batch.common.writer.MajorWriter;
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
public class UpdateMajorConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final MajorClientReader majorClientReader;
    private final MajorWriter majorWriter;
    private final MajorPartitioner majorPartitioner;
    private final CreateMajorProcessor createMajorProcessor;

    private final int GRID_SIZE = 5;

    @Bean
    public Job updateMajor() {
        return new JobBuilder("updateMajor", jobRepository)
                .start(updateMajorMasterStep())
                .build();
    }

    @Bean
    public Step updateMajorMasterStep() {
        return new StepBuilder("updateMajorMasterStep", jobRepository)
                .partitioner("updateMajorWorkerStep", majorPartitioner)
                .step(updateMajorWorkerStep())
                .gridSize(GRID_SIZE)
                .taskExecutor(majorTaskExecutor())
                .build();
    }

    @Bean
    public Step updateMajorWorkerStep() {
        return new StepBuilder("updateMajorWorkerStep", jobRepository)
                .<CrawledMajor, CreateMajor>chunk(1000, transactionManager)
                .reader(majorClientReader)
                .processor(createMajorProcessor)
                .writer(majorWriter)
                .build();
    }

    @Bean
    public TaskExecutor majorTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(GRID_SIZE);
        executor.setMaxPoolSize(GRID_SIZE);
        executor.setQueueCapacity(GRID_SIZE);
        executor.setThreadNamePrefix("major-batch-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}
