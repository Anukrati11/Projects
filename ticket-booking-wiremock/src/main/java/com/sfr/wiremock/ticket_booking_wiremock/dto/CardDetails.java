package com.sfr.wiremock.ticket_booking_wiremock.dto;


import java.time.LocalDate;

public class CardDetails {
    private final String number;
    private final LocalDate expiry;

    public CardDetails(String number, LocalDate expiry) {
        this.number = number;
        this.expiry = expiry;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getExpiry() {
        return expiry;
    }
}
