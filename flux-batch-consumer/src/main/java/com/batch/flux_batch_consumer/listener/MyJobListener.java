package com.batch.flux_batch_consumer.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MyJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Started the Job at :" + jobExecution.getStartTime()+" with status : "+jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("Completed the Job at :" + jobExecution.getEndTime()+" with status : "+jobExecution.getStatus());
    }

}
