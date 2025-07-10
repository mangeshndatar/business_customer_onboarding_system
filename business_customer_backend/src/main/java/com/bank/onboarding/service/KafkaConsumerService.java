package com.bank.onboarding.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.bank.onboarding.entity.ApplicationModel;
import com.bank.onboarding.enums.ApplicationStatus;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "application-topic", groupId = "application-group")
    public void consumeMessage(ApplicationModel application) {
        try {
            logger.info("Message received from Kafka: {}", application);
            
            // Process the application (simulate processing)
            application.setStatus(ApplicationStatus.PENDING);
            
            // Send notification to Angular frontend via WebSocket
            messagingTemplate.convertAndSend("/topic/notifications", application);
            
            logger.info("Notification sent to frontend for application ID: {}", application.getApplicationId());
        } catch (Exception e) {
            logger.error("Error processing message from Kafka: {}", e.getMessage());
        }
    }
}