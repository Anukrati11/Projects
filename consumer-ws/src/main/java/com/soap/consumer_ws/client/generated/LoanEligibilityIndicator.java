
package com.soap.consumer_ws.client.generated;

import jakarta.jws.Oneway;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.2
 * Generated source version: 3.0
 * 
 */
@WebService(name = "LoanEligibilityIndicator", targetNamespace = "http://www.javatechie.com/spring/soap/api/loanEligibility")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface LoanEligibilityIndicator {


    /**
     * 
     * @param customerRequest
     */
    @WebMethod(operationName = "Customer")
    @Oneway
    public void customer(
        @WebParam(name = "CustomerRequest", targetNamespace = "http://www.javatechie.com/spring/soap/api/loanEligibility", partName = "CustomerRequest")
        CustomerRequest customerRequest);

}