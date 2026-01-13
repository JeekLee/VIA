package com.via.runnable.batch.content.scheduler;

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
public class CourseScheduler {
    private final JobLauncher jobLauncher;
    private final Job updateCourse;


    /**
     * Run update course entity.
     * <p>
     * Scheduled at every monday, 3AM
     */
    @Scheduled(cron = "0 0 3 * * 2", zone = "Asia/Seoul")
    public void runUpdateCourseEntity() {
        try {
            log.info("======================================");
            log.info("Starting UpdateCourseEntity Batch Job");
            log.info("======================================");

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(updateCourse, jobParameters);

            log.info("======================================");
            log.info("UpdateCourseEntity Batch Job Completed");
            log.info("Start: {}, End: {}, Duration: {}m",
                    jobExecution.getStartTime(),
                    jobExecution.getEndTime(),
                    Duration.between(Objects.requireNonNull(jobExecution.getStartTime()), jobExecution.getEndTime()).toMinutes());
            log.info("======================================");
        } catch (Exception e) {
            log.error("UpdateCourseEntity Batch Job failed", e);
        }
    }
}
