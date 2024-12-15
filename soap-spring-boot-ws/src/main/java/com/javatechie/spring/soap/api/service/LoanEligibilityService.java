package com.javatechie.spring.soap.api.service;

import com.javatechie.spring.soap.api.loaneligibility.Acknowledgement;
import com.javatechie.spring.soap.api.loaneligibility.CustomerRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanEligibilityService {
    public Acknowledgement checkLoanEligibility(CustomerRequest customerRequest) {
        Acknowledgement acknowledgement = new Acknowledgement();
        List<String> mismatchCriteriaList = acknowledgement.getCriteriaMismatch();
//        I need to add to mismatchCriteriaList if person's age is not between 30 to 60 or
//        if yearly income is not greater than 2000000 or
//        if cibilscore is less than 700 or
//        if employment mode is not permanent
        if (!(customerRequest.getAge() >= 30 && customerRequest.getAge() <= 60) ||
                customerRequest.getYearlyIncome() < 200000 ||
                customerRequest.getCibilScore() < 700 ||
                !customerRequest.getEmploymentMode().equals("permanent")) {
            mismatchCriteriaList.add("Person is not matching the loan criteria");
        }
        if (!mismatchCriteriaList.isEmpty()) {
            acknowledgement.setApprovedAmount(0);
            acknowledgement.setIsEligible(false);
            acknowledgement.getCriteriaMismatch().add("Customer is not eligible for loan due to mismatch criteria");
        } else {
            acknowledgement.setApprovedAmount(500000);
            acknowledgement.setIsEligible(true);
            mismatchCriteriaList.clear();
        }
        return acknowledgement;
    }
}
