//package com.camunda8.learn.sample_camunda.controller;
//
//import com.camunda8.learn.sample_camunda.dto.LeaveRequest;
//import io.camunda.zeebe.client.ZeebeClient;
//import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/leave")
//public class LeaveController {
//
//    @Autowired
//    private ZeebeClient zeebeClient;  // Zeebe Client to interact with the Zeebe engine
//
//    @PostMapping("/apply")
//    public ResponseEntity<String> applyForLeave(@RequestBody LeaveRequest leaveRequest) {
//        // Prepare the process variables to be passed to the BPMN process
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("employeeId", leaveRequest.getEmployeeId());
//        variables.put("requestedLeaveDays", leaveRequest.getRequestedLeaveDays());
//
//        // Start the process instance using ZeebeClient
//        ProcessInstanceEvent event = zeebeClient
//                .newCreateInstanceCommand()
//                .bpmnProcessId("leave-application-process")  // BPMN Process ID
//                .latestVersion()  // Start the latest deployed version
//                .variables(variables)  // Pass variables
//                .send()
//                .join();  // Blocking call to wait for the result
//
//        // Return response with the process instance key
//        return ResponseEntity.ok("Leave application process started with instance key: " + event.getProcessInstanceKey());
//    }
//
//
//}
//
