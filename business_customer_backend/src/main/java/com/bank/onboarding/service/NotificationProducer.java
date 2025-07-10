package com.bank.onboarding.service;


import com.bank.onboarding.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducer {
    
	@Autowired
    private KafkaTemplate<String, Notification> kafkaTemplate;
    
    @Value("${kafka.topic.notifications}")
    private String notificationTopic;
    
    public void sendNotification(Notification notification) {
//        log.info("Sending notification: {}", notification);
        
        CompletableFuture<SendResult<String, Notification>> future = 
            kafkaTemplate.send(notificationTopic, notification.getUserId(), notification);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
//                log.info("Sent notification with key=[{}] offset=[{}]", 
//                    notification.getUserId(), 
//                    result.getRecordMetadata().offset());
            } else {
//                log.error("Unable to send notification", ex);
            }
        });
    }
}
