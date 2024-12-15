package com.sfr.wiremock.ticket_booking_wiremock.dto;

public class PaymentUpdateResponse {

    private String status;

    public PaymentUpdateResponse(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
