package com.zaghir.batch.batchspringmodel.steps.liteners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class ListenerStep1 implements StepExecutionListener {

	Logger logger = LoggerFactory.getLogger(ListenerStep1.class);
	@Override
	public ExitStatus afterStep(StepExecution arg0) {
		logger.info("ListenerStep1  afterStep ---> ");
		return null;
	}

	@Override
	public void beforeStep(StepExecution arg0) {
		logger.info("ListenerStep1  beforeStep ---> ");		
	}
	

}
