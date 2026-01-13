package com.via.runnable.batch.common.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class CorporationSyncScheduler {
    private final JobLauncher jobLauncher;
    private final Job updateCorporation;

    // Runs every year on April 15th at 5:00 AM
    @Scheduled(cron = "0 0 3 15 4 *", zone = "Asia/Seoul")
    public void runUpdateCorporation() {
        try {
            log.info("======================================");
            log.info("Starting UpdateCorporation Batch Job");
            log.info("======================================");

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(updateCorporation, jobParameters);

            log.info("======================================");
            log.info("UpdateCorporation Batch Job Completed");
            log.info("Start: {}, End: {}, Duration: {}m",
                    jobExecution.getStartTime(),
                    jobExecution.getEndTime(),
                    Duration.between(Objects.requireNonNull(jobExecution.getStartTime()), jobExecution.getEndTime()).toMinutes());
            log.info("======================================");
        } catch (Exception e) {
            log.error("UpdateCorporation Batch Job failed", e);
        }
    }
}
