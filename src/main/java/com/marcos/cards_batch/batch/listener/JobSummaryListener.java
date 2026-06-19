package com.marcos.cards_batch.batch.listener;

import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobSummaryListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long totalRead = 0L;
        Long totalWrite = 0L;
        Long totalSkip = 0L;

        for (StepExecution step : jobExecution.getStepExecutions()) {
            totalRead += step.getReadCount();
            totalWrite += step.getWriteCount();
            totalSkip += step.getSkipCount();
        }

        log.info("======== SUMMARY JOB ========");
        log.info("Status: {}", jobExecution.getStatus());
        log.info("Read: {}", totalRead);
        log.info("Skip: {}", totalSkip);
        log.info("Start: {}", jobExecution.getCreateTime());
        log.info("Finish: {}", jobExecution.getEndTime());
        log.info("============================");
    }
}
