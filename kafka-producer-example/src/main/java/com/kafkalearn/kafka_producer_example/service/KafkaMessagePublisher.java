package com.kafkalearn.kafka_producer_example.service;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    // if you want to talk to kafka then you need to use the default Kafka teamplate
    @Autowired
    private KafkaTemplate<String, Object > template;

    public void sendMessageToTopic(String message){
        //return type of send is completable future
        CompletableFuture<SendResult<String, Object>> future = template.send("bulk-producer-topic2", message);
        //instead of .get of the future which will slow down the process we are using callback functions as below ->
        future.whenComplete((result, ex) -> {
            if(ex == null){
                System.out.println("Message = "+ message+ " sent successfully with offset = "+ result.getRecordMetadata().offset()
                        + " in partition "+ result.getRecordMetadata().partition()+ " of topic = "+ result.getRecordMetadata().topic());
            }
            else {
                System.out.println("Unable to send message = "+ message+" due to this error: "+ex.getMessage());
            }
        });
    }
}
