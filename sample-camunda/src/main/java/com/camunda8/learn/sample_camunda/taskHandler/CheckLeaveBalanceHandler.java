package com.camunda8.learn.sample_camunda.taskHandler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CheckLeaveBalanceHandler {

    @Autowired
    private final ZeebeClient zeebeClient;

    public CheckLeaveBalanceHandler(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    /**
     * Service Task 1 - checking leave balance
     * @param jobClient
     * @param job
     */
    @io.camunda.zeebe.spring.client.annotation.JobWorker(type = "check-leave-balance", autoComplete = true)
    public void checkLeaveBalanceTask(JobClient jobClient, ActivatedJob job) {
        checkLeaveBalance(jobClient, job);
    }

    /**
     * Service Task 2 - update leave balance
     * @param jobClient
     * @param job
     */
    @io.camunda.zeebe.spring.client.annotation.JobWorker(type = "update-leave-balance", autoComplete = true)
    public void updateLeave(JobClient jobClient, ActivatedJob job) {

        // Retrieve the process variables passed to the service task
        Map<String, Object> variables = job.getVariablesAsMap();
        String status = (String) variables.get("decision");
        if(status.equals("Reject")){
            jobClient.newFailCommand(job.getKey()).retries(0).errorMessage("Insufficient leave balance").send().join();
        }else{
            handleUpdateLeaveBalance(jobClient, job, variables);  // Call the method to handle the logic
        }

    }

    private void handleUpdateLeaveBalance(JobClient jobClient, ActivatedJob job, Map<String, Object> variables){
        String requestedLeaveDays = (String) variables.get("noOfDays");
        Integer noOfDays = Integer.parseInt(requestedLeaveDays);

        // deduct the requested days from the available balance
        int updatedLeaveBalance = getAvailableLeaveDays() - noOfDays;

        // Prepare result variables to pass to the next task in the process
        Map<String, Object> resultVariables = new HashMap<>();
        resultVariables.put("updatedLeaveBalance", updatedLeaveBalance);

        // Complete the task and send the result back to Zeebe, continuing the process
        jobClient.newCompleteCommand(job.getKey())
                .variables(resultVariables)
                .send()
                .join();
    }

    // This method is the handler for the "check-leave-balance" task
    private void checkLeaveBalance(JobClient jobClient, ActivatedJob job) {
        handleCheckLeaveBalance(jobClient, job);  // Call the method to handle the logic
    }

    private void handleCheckLeaveBalance(JobClient jobClient, ActivatedJob job) {
        // Retrieve the process variables passed to the service task
        Map<String, Object> variables = job.getVariablesAsMap();

        // Extract requested leave days from the process variables
        String requestedLeaveDays = (String) variables.get("noOfDays");
        Integer noOfDays = Integer.parseInt(requestedLeaveDays);

        // Fetch the employee's available leave days (this could come from a database or service)
        int availableLeaveDays = getAvailableLeaveDays();

        // Logic to check if the employee has sufficient leave balance
        boolean sufficientLeave = noOfDays <= availableLeaveDays;

        // Prepare result variables to pass to the next task in the process
        Map<String, Object> resultVariables = new HashMap<>();
        resultVariables.put("sufficientLeave", sufficientLeave);

        // Complete the task and send the result back to Zeebe, continuing the process
        jobClient.newCompleteCommand(job.getKey())
                .variables(resultVariables)
                .send()
                .join();
    }

    // Simulated method to fetch available leave days from a service or database
    private int getAvailableLeaveDays() {
        // For now, assume every employee has 10 leave days (could be fetched from a real database)
        return 10;
    }




}
