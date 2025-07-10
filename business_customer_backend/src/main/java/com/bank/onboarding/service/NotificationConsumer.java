package com.bank.onboarding.service;


import com.bank.onboarding.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    
	@Autowired
    private  SimpMessagingTemplate messagingTemplate;
    
    @KafkaListener(topics = "${kafka.topic.notifications}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeNotification(Notification notification) {
//        log.info("Received notification: {}", notification);
        
        // Send to specific user via WebSocket
        messagingTemplate.convertAndSendToUser(
            notification.getUserId(),
            "/topic/notifications",
            notification
        );
        
        // Also broadcast to all users (optional)
        messagingTemplate.convertAndSend(
            "/topic/broadcast-notifications",
            notification
        );
    }
}
