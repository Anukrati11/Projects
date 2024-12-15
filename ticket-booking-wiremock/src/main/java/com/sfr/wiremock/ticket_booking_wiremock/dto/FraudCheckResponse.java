package com.sfr.wiremock.ticket_booking_wiremock.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FraudCheckResponse {
    private final boolean blacklisted;
    //more fraud flags to go here..

    @JsonCreator
    public FraudCheckResponse(@JsonProperty("blacklisted") final boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public boolean isBlacklisted() {
        return blacklisted;
    }
}
