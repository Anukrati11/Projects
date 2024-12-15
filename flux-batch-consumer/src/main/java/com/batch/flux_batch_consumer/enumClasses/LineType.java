package com.batch.flux_batch_consumer.enumClasses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LineType {
    H("HEADER"),
    M("DATA"),
    T("Tail");

    public String getValue() {
        return value;
    }

    private final String value;
}
