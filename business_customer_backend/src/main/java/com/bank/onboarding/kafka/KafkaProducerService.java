package com.bank.onboarding.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.bank.onboarding.dto.ApplicationEvent;
import com.bank.onboarding.dto.ApplicationQueuePayload;
import com.bank.onboarding.entity.Application;

@Component
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaApplicationQueueTemplate;

    @Value("${kafka.topic.registration}")
    private String topicName;

    @Value("${kafka.topic.applicant-submissions-event}")
    private String notificationTopicName;

    public void sendApplication(Application payload) {
        // log.info("application received to the producer: "+
        // payload.getApplicationId());
        kafkaApplicationQueueTemplate.send(topicName, payload.getApplicationId(), payload);
    }

    public void sendNotification(ApplicationEvent payload) {
        // log.info("notification received to the producer: "+
        // payload.getApplicationId());
        kafkaApplicationQueueTemplate.send(notificationTopicName, payload.getApplicationId(), payload);
    }
}
