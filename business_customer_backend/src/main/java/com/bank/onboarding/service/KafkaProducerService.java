package com.bank.onboarding.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bank.onboarding.entity.ApplicationModel;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "application-topic";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(ApplicationModel application) {
        try {
            kafkaTemplate.send(TOPIC, application);
            logger.info("Message sent to Kafka topic: {}", application);
        } catch (Exception e) {
            logger.error("Error sending message to Kafka: {}", e.getMessage());
        }
    }
}