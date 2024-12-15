package com.sfr.wiremock.ticket_booking_wiremock.controller;

import com.sfr.wiremock.ticket_booking_wiremock.dto.PaymentUpdateResponse;
import com.sfr.wiremock.ticket_booking_wiremock.dto.TicketBookingPaymentRequest;
import com.sfr.wiremock.ticket_booking_wiremock.dto.TicketBookingResponse;
import com.sfr.wiremock.ticket_booking_wiremock.service.TicketBookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TicketBookingController {
    private TicketBookingService ticketBookingService;

    public TicketBookingController(TicketBookingService ticketBookingService) {
        this.ticketBookingService = ticketBookingService;
    }

    /**
     * test change
     */
    @PostMapping("/bookTicket")
    TicketBookingResponse payForTicket(final TicketBookingPaymentRequest ticketBookingPaymentRequest){
        return ticketBookingService.payForBooking(ticketBookingPaymentRequest);
    }

    @PostMapping("/updatePayment")
    PaymentUpdateResponse updatePaymentDetails(final TicketBookingPaymentRequest ticketBookingPaymentRequest){
        return ticketBookingService.updatePaymentDetails(ticketBookingPaymentRequest);
    }

    @PostMapping("/batchPayment")
    List<TicketBookingResponse> batchPayment(List<TicketBookingPaymentRequest> bookingPayment){
        return ticketBookingService.batchPayment(bookingPayment);
    }
}
