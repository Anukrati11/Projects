package com.batch.flux_batch_consumer.enumClasses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    SUCCESS("SUCCESS"),
    FAILED("FAILED"),
    INVALID("INVALID"),
    IN_PROGRESS("IN_PROGRESS"),

    DUPLICATE("DUPLICATE");


    private final String value;

    public String getValue() {
        return value;
    }
}
