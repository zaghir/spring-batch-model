package com.zaghir.batch.batchspringmodel.configuration;

import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;

public class CustomCompletionPolicy implements CompletionPolicy {

	private String commitInterval = "1500";

	private Integer chunckSize;
	private Integer totalProcessed;

	public CustomCompletionPolicy(String commitInterval) {
		super();
		this.commitInterval = commitInterval;
	}

	@Override
	public boolean isComplete(RepeatContext context) {

		return totalProcessed >= chunckSize;
	}

	@Override
	public boolean isComplete(RepeatContext context, RepeatStatus status) {
		if (RepeatStatus.FINISHED == status) {
			return true;
		} else {
			return isComplete(context);
		}
	}

	@Override
	public RepeatContext start(RepeatContext context) {

		chunckSize = Integer.valueOf(getCommitInterval());

		totalProcessed = 0;

		return context;
	}

	@Override
	public void update(RepeatContext context) {

		totalProcessed++;
	}

	public String getCommitInterval() {
		return commitInterval;
	}

	public void setCommitInterval(String commitInterval) {
		this.commitInterval = commitInterval;
	}
}
