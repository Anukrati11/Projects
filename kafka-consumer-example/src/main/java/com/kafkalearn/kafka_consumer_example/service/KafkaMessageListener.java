package com.kafkalearn.kafka_consumer_example.service;

import ch.qos.logback.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @RetryableTopic()
    @KafkaListener(topics = "bulk-producer-topic2", groupId = "consumer-group-1")
    public void consume1(String message){
        if(message.equals("Helo")){
            log.info("this message is not consumed and sent for retry.");
            throw new RuntimeException("This message is errorful");
        }
        else{
            log.info("Consumer1 consumes the message = "+message, message);
        }

    }

    @DltHandler
    public void listenDLT(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset){
        log.info("DLT Received for message : {} , from{}, offset {}", message, topic, offset);
    }

//    @KafkaListener(topics = "bulk-producer-topic2", groupId = "consumer-group-2")
//    public void consume2(String message){
//        log.info("Consumer2 consumes the message = "+message, message);
//
//    }
//
//    @KafkaListener(topics = "bulk-producer-topic2", groupId = "consumer-group-2")
//    public void consume3(String message){
//        log.info("Consumer3 consumes the message = "+message, message);
//
//    }
//
//    @KafkaListener(topics = "bulk-producer-topic2", groupId = "consumer-group-2")
//    public void consume4(String message){
//        log.info("Consumer4 consumes the message = "+message, message);
//
//    }

}
