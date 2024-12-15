package com.javatechie.spring.soap.api.service;

import com.javatechie.spring.soap.api.loaneligibility.Acknowledgement;
import com.javatechie.spring.soap.api.loaneligibility.CustomerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoanEligibilityServiceTest {

    @InjectMocks
    private LoanEligibilityService service;

    @Test
    public void checkLoanEligibilityOk(){
        // Preparer
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAge(35);
        customerRequest.setYearlyIncome(3000000);
        customerRequest.setCibilScore(750);
        customerRequest.setEmploymentMode("permanent");

        // Execute
        Acknowledgement acknowledgement = service.checkLoanEligibility(customerRequest);

        // Validate
        Assertions.assertEquals(500000, acknowledgement.getApprovedAmount());
        Assertions.assertEquals(acknowledgement.getCriteriaMismatch().size(), 0);
    }

    @Test
    public void checkLoanEligibilityKO(){
        // Preparer
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setAge(25);
        customerRequest.setYearlyIncome(3000000);
        customerRequest.setCibilScore(750);
        customerRequest.setEmploymentMode("permanent");

        // Execute
        Acknowledgement acknowledgement = service.checkLoanEligibility(customerRequest);

        // Validate
        Assertions.assertEquals(0, acknowledgement.getApprovedAmount());
        Assertions.assertEquals(acknowledgement.getCriteriaMismatch().size(), 2);
    }
}
