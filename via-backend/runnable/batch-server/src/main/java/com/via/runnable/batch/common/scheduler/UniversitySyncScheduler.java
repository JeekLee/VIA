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
public class UniversitySyncScheduler {
    private final JobLauncher jobLauncher;
    private final Job updateUniversity;

    // Runs every year on March 1st at 3:00 AM
    @Scheduled(cron = "0 0 3 1 3 *", zone = "Asia/Seoul")
    public void runUpdateUniversityDocument() {
        try {
            log.info("======================================");
            log.info("Starting UpdateUniversity Batch Job");
            log.info("======================================");

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(updateUniversity, jobParameters);

            log.info("======================================");
            log.info("UpdateUniversity Batch Job Completed");
            log.info("Start: {}, End: {}, Duration: {}m",
                    jobExecution.getStartTime(),
                    jobExecution.getEndTime(),
                    Duration.between(Objects.requireNonNull(jobExecution.getStartTime()), jobExecution.getEndTime()).toMinutes());
            log.info("======================================");
        } catch (Exception e) {
            log.error("UpdateUniversity Batch Job failed", e);
        }
    }
}
