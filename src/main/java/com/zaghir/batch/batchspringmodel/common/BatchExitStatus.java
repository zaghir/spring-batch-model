package com.zaghir.batch.batchspringmodel.common;

import org.springframework.batch.core.ExitStatus;

public class BatchExitStatus extends ExitStatus {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String CONTINUE = "CONTINUE";
    public static final String FINISHED = "FINISHED";
    public static final String GENERATION_RAPPORT = "GENERATION_RAPPORT";
    public static final String PARTIAL_COMPLETED = "PARTIAL_COMPLETED";

    private String status;

    /**
     * 
     * @param status
     */
    public BatchExitStatus(String status) {
        super(status);
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
