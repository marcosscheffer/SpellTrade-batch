package com.marcos.cards_batch.batch.listener;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StepSummaryListener implements StepExecutionListener{

    @Override
    public @Nullable ExitStatus afterStep(StepExecution stepExecution) {
        log.info("======== SUMMARY STEP ========");
        log.info("Name: {}", stepExecution.getStepName());
        log.info("Status: {}", stepExecution.getStatus());
        log.info("Read: {}", stepExecution.getReadCount());
        log.info("Skip: {}", stepExecution.getSkipCount());
        log.info("=============================");

        return stepExecution.getExitStatus();
    }
}
