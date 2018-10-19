package com.example.machinehealthchecksystem.config;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ScheduledTask implements Serializable {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Scheduled(fixedRate = 20000)
    public void scheduleHealthCheck() {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | ConcurrencyFailureException e) {
            e.getMessage();
        }
    }
}
