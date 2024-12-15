package com.camunda8.learn.sample_camunda;

//import io.camunda.zeebe.spring.client.EnableZeebeClient;
//import io.camunda.zeebe.spring.client.annotation.Deployment;
//import io.camunda.zeebe.spring.client.annotation.ZeebeDeployment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableZeebeClient
//@ZeebeDeployment(classPathResources = "leave_flow_camunda8.bpmn")
//@Deployment(resources = "classpath:leave_flow_camunda8.bpmn")
public class SampleCamundaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleCamundaApplication.class, args);
	}

}
