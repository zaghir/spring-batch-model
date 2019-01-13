package com.zaghir.batch.batchspringmodel.steps.liteners;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;

import com.zaghir.batch.batchspringmodel.common.BatchExitStatus;
import com.zaghir.batch.batchspringmodel.common.DateUtils;


/**
 * 
 * @author U-FRM-Formation
 *
 */
public class JobListener implements JobExecutionListener {
    private static Logger logger = LoggerFactory.getLogger(JobListener.class);

    private static final String PLUS = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++";

    @Override
    public void afterJob(JobExecution jobExecution) {

        jobExecution.getExecutionContext().put("TEMPS_ECOULE", DateUtils.getTempsEcoule(jobExecution.getCreateTime()));
        jobExecution.getExecutionContext().put("POURCENTAGE_AVANCEMENT", 100);

        StringBuilder protocol = new StringBuilder();

        ExitStatus exitStatus = BatchExitStatus.COMPLETED;
        jobExecution.setExitStatus(exitStatus);

        protocol.append("\n" + PLUS + "\n");
        protocol.append("Date/Heure d'exécution " + jobExecution.getJobInstance().getJobName() + " \n");
        protocol.append("  Started     : " + jobExecution.getStartTime() + "\n");
        protocol.append("  Finished    : " + jobExecution.getEndTime() + "\n");
        protocol.append("  Exit-Code   : " + jobExecution.getExitStatus().getExitCode() + "\n");
        protocol.append(PLUS + " \n");
        protocol.append("Job-Parameter: \n");
        protocol.append(PLUS + "\n");
        logger.info(protocol.toString());

    }

    private Boolean isExceptionExists(JobExecution jobExecution) {
        Boolean isExceptionExists = Boolean.FALSE;

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            if (stepExecution.getFailureExceptions() != null && !stepExecution.getFailureExceptions().isEmpty()) {
                isExceptionExists = Boolean.TRUE;
            }
        }

        return isExceptionExists;
    }

    private StringBuilder getExceptionLog(JobExecution jobExecution) {
        StringBuilder protocol = new StringBuilder();
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            protocol.append("\n---------------------------------------------------------------------------------------\n");
            protocol.append("Début de step: " + stepExecution.getStartTime() + "\n");
            protocol.append("Fin de Step" + stepExecution.getEndTime() + "\n");
            protocol.append(stepExecution.getSummary()).append("\n");
            protocol.append(getExceptionDetail(stepExecution));
        }
        return protocol;
    }

    private StringBuilder getExceptionDetail(StepExecution stepExecution) {
        StringBuilder protocol = new StringBuilder();

        if (stepExecution.getFailureExceptions() != null && !stepExecution.getFailureExceptions().isEmpty()) {
            protocol.append("\nListe des exceptions ");
            Set<Throwable> exceptions = new HashSet<>(stepExecution.getFailureExceptions());
            for (Throwable exception : exceptions) {
                String className = exception.getClass().getSimpleName();
                String message = exception.getMessage();
                if (exception.getCause() instanceof InvocationTargetException) {
                    InvocationTargetException targetException = (InvocationTargetException) exception.getCause();
                    message = targetException.getTargetException().getMessage();
                    className = targetException.getTargetException().getClass().getSimpleName();
                }
                protocol.append("\nException " + className);
                if (message != null) {
                    protocol.append("\n   Details : " + message);
                }
            }
        }

        return protocol;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info(">>JobListener> beforeJob");
    }

}
