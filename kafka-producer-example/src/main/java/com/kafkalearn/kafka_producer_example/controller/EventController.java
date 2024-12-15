package com.kafkalearn.kafka_producer_example.controller;

import com.kafkalearn.kafka_producer_example.service.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka-producer-app")
public class EventController {

    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publicMessage(@PathVariable String message){
        try {
//            for(int i=0;i<10000;i++){
//                kafkaMessagePublisher.sendMessageToTopic(message+" : "+i);
//            }

            kafkaMessagePublisher.sendMessageToTopic(message);

            return ResponseEntity.ok("Messages published Successfully..");
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
