package com.sfr.wiremock.ticket_booking_wiremock.service.stubbing;

import com.sfr.wiremock.ticket_booking_wiremock.dto.CardDetails;
import com.sfr.wiremock.ticket_booking_wiremock.dto.PaymentUpdateResponse;
import com.sfr.wiremock.ticket_booking_wiremock.dto.TicketBookingPaymentRequest;
import com.sfr.wiremock.ticket_booking_wiremock.dto.TicketBookingResponse;
import com.sfr.wiremock.ticket_booking_wiremock.gateway.PaymentProcessorGateway;
import com.sfr.wiremock.ticket_booking_wiremock.service.TicketBookingService;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.sfr.wiremock.ticket_booking_wiremock.dto.TicketBookingResponse.BookingResponseStatus.SUCCESS;

public class TicketBookingServiceGeneralStubbingTest {

    private TicketBookingService ticketBookingService;
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer();
        configureFor("localhost", 8081);
        wireMockServer.start();


        // Check if WireMock server started successfully
        if (!wireMockServer.isRunning()) {
            throw new RuntimeException("WireMock server didn't start correctly");
        }


        PaymentProcessorGateway paymentProcessorGateway = new PaymentProcessorGateway("localhost", wireMockServer.port());
        ticketBookingService = new TicketBookingService(paymentProcessorGateway);
    }



    @Test
    void updatePaymentRequestNormalStub() {

        //given
        wireMockServer.stubFor(any((anyUrl())).willReturn(ok()));

        //when
        TicketBookingPaymentRequest tbpr = new TicketBookingPaymentRequest("1111",
                200.0,
                new CardDetails("1111-1111-1111", LocalDate.now()));

        PaymentUpdateResponse response = ticketBookingService.updatePaymentDetails(tbpr);

        //then
        Assertions.assertThat(response.getStatus()).isEqualTo("SUCCESS");
    }

    @Test
    public void updatePaymentRequestJsonResponseStub() {

        // Stubbing for other gateway services calling
        wireMockServer.stubFor(post(urlPathEqualTo("/payments"))
                .withRequestBody(equalToJson("{\n" +
                    "\"cardNumber\" : \"1111-1111-1111-1111\",\n" +
                    "\"cardExpiryDate\" : \"2024-10-15\",\n" +
                    "\"amount\" : 100.5\n" +
                    "}"))
                .willReturn(okJson("{\n" +
                    "\"paymentId\" : \"2222\",\n" +
                    "\"paymentResponseStatus\" : \"SUCCESS\"\n" +
                    "}")));


        // Prepare
        TicketBookingPaymentRequest ticketBookingPaymentRequest = new TicketBookingPaymentRequest("111", 100.5, new CardDetails("1111-1111-1111-1111", LocalDate.of(2024, 10, 15)));
        TicketBookingResponse expectedticketBookingResponse = new TicketBookingResponse("111", "2222", SUCCESS);

        // Execute
        TicketBookingResponse res = ticketBookingService.payForBooking(ticketBookingPaymentRequest);

        // Verify
        Assertions.assertThat(res.getBookingId()).isEqualTo(expectedticketBookingResponse.getBookingId());
        Assertions.assertThat(res.getPaymentId()).isEqualTo(expectedticketBookingResponse.getPaymentId());
        Assertions.assertThat(res.getBookingResponseStatus()).isEqualTo(expectedticketBookingResponse.getBookingResponseStatus());

    }

    @Test
    void multipleStubbingPayForBooking() {

        //given
        wireMockServer.stubFor(post(urlPathEqualTo("/payments"))
                .withRequestBody(equalToJson("{\n" +
                        "\"cardNumber\" : \"1111-1111-1111-1111\",\n" +
                        "\"cardExpiryDate\" : \"2020-08-03\",\n" +
                        "\"amount\" : 200.0\n" +
                        "}"))
                .willReturn(okJson("{\n" +
                        "\"paymentId\" : \"3333\",\n" +
                        "\"paymentResponseStatus\" : \"SUCCESS\"\n" +
                        "}")));

        wireMockServer.stubFor(get(urlPathEqualTo("/fraudCheck/1111-1111-1111-1111"))
                .willReturn(okJson("{" +
                        "  \"blacklisted\": \"false\"" +
                        "}")));

        TicketBookingResponse expectedTicketBookingResponse = new TicketBookingResponse("1111", "3333", SUCCESS);

        //when
        TicketBookingPaymentRequest tbpr = new TicketBookingPaymentRequest("1111",
                200.0,
                new CardDetails("1111-1111-1111-1111", LocalDate.of(2020, 8, 3)));
        tbpr.setFraudAlert(true);
        TicketBookingResponse response = ticketBookingService.payForBooking(tbpr);

        //then
        Assertions.assertThat(response.getBookingId()).isEqualTo(expectedTicketBookingResponse.getBookingId());
        Assertions.assertThat(response.getBookingResponseStatus()).isEqualTo(expectedTicketBookingResponse.getBookingResponseStatus());
        Assertions.assertThat(response.getPaymentId()).isEqualTo(expectedTicketBookingResponse.getPaymentId());


    }

        @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }
}
