package com.via.runnable.batch.common.job;

import com.via.common.app.career.dto.CreateCorporation;
import com.via.common.crawler.dto.CrawledCorporation;
import com.via.runnable.batch.common.partitioner.CorporationPartitioner;
import com.via.runnable.batch.common.processor.CreateCorporationProcessor;
import com.via.runnable.batch.common.reader.CorporationClientReader;
import com.via.runnable.batch.common.writer.CorporationWriter;
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
public class UpdateCorporationConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final CorporationClientReader corporationClientReader;
    private final CorporationWriter corporationWriter;
    private final CorporationPartitioner corporationPartitioner;
    private final CreateCorporationProcessor createCorporationProcessor;

    private final int GRID_SIZE = 5;

    @Bean
    public Job updateCorporation() {
        return new JobBuilder("updateCorporation", jobRepository)
                .start(updateCorporationMasterStep())
                .build();
    }

    @Bean
    public Step updateCorporationMasterStep() {
        return new StepBuilder("updateCorporationMasterStep", jobRepository)
                .partitioner("updateCorporationWorkerStep", corporationPartitioner)
                .step(updateCorporationWorkerStep())
                .gridSize(GRID_SIZE)
                .taskExecutor(corporationTaskExecutor())
                .build();
    }

    @Bean
    public Step updateCorporationWorkerStep() {
        return new StepBuilder("updateCorporationWorkerStep", jobRepository)
                .<CrawledCorporation, CreateCorporation>chunk(1000, transactionManager)
                .reader(corporationClientReader)
                .processor(createCorporationProcessor)
                .writer(corporationWriter)
                .build();
    }

    @Bean
    public TaskExecutor corporationTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(GRID_SIZE);
        executor.setMaxPoolSize(GRID_SIZE);
        executor.setQueueCapacity(GRID_SIZE);
        executor.setThreadNamePrefix("corporation-batch-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}
