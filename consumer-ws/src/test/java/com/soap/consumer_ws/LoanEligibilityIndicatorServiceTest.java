package com.soap.consumer_ws;

//import com.soap.consumer_ws.client.generated.Acknowledgement;
//import com.soap.consumer_ws.client.generated.CustomerRequest;
//import com.soap.consumer_ws.client.generated.LoanEligibilityIndicatorService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class LoanEligibilityIndicatorServiceTest {
//    @Autowired
//    private LoanEligibilityIndicatorService loanEligibilityIndicatorService;
//
//    @Test
//    public void testLoanEligibilityOK() {
//        CustomerRequest customerRequest = new CustomerRequest();
//        customerRequest.setCustomerName("Anukrati");
//        customerRequest.setAge(35);
//        customerRequest.setYearlyIncome(30000000);
//        customerRequest.setCibilScore(750);
//        customerRequest.setEmploymentMode("permanent");
//        Acknowledgement acknowledgement = loanEligibilityIndicatorService.getLoanEligibilityIndicatorSoap11().customer(customerRequest);
//
//        // Send the request and assert the response
//        // This is just an example, replace with your actual request and response
//        Acknowledgement response = loanEligibilityIndicatorService.getLoanEligibilityIndicatorSoap11().customer(customerRequest);
//        assertEquals(true, response.isEligible());
//        assertEquals(500000, response.getApprovedAmount());
//    }
//
//}
// Java
import com.soap.consumer_ws.client.generated.Acknowledgement;
import com.soap.consumer_ws.client.generated.CustomerRequest;
import com.soap.consumer_ws.client.generated.LoanEligibilityIndicatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreators;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static org.springframework.ws.test.client.RequestMatchers.soapEnvelope;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

@SpringBootTest
public class LoanEligibilityIndicatorServiceTest {

    @Autowired
    private LoanEligibilityIndicatorService service;

    @Test
    public void testGetLoanEligibilityIndicatorSoap11() {
        MockWebServiceServer mockServer = MockWebServiceServer.createServer(service);

        // Create a request message
        Source requestPayload = new StringSource(
                "<customerRequest xmlns='http://www.javatechie.com/spring/soap/api/loanEligibility'>" +
                        "<customerName>John Doe</customerName>" +
                        "<age>30</age>" +
                        "<yearlyIncome>70000</yearlyIncome>" +
                        "<cibilScore>700</cibilScore>" +
                        "<employmentMode>EMPLOYED</employmentMode>" +
                        "</customerRequest>");

        // Create a response message
        Source responsePayload = new StringSource(
                "<customerResponse xmlns='http://www.javatechie.com/spring/soap/api/loanEligibility'>" +
                        "<isEligible>true</isEligible>" +
                        "<approvedAmount>500000</approvedAmount>" +
                        "</customerResponse>");

        // Expect the request and respond with the response
        mockServer.expect(soapEnvelope(requestPayload)).andRespond(withPayload(responsePayload));

        // Send the request and assert the response
        // This is just an example, replace with your actual request and response
//        CustomerRequest request = new CustomerRequest("John Doe", 30, 70000, 700, "EMPLOYED");
//        Acknowledgement response = service.getLoanEligibilityIndicatorSoap11().customer(request);
//        assertEquals(true, response.isEligible());
//        assertEquals(500000, response.getApprovedAmount());
    }
}