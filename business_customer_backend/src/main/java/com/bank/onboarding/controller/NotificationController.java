package com.bank.onboarding.controller;


import com.bank.onboarding.entity.Notification;
import com.bank.onboarding.service.NotificationProducer;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {
    
	@Autowired
    private  NotificationProducer notificationProducer;
    
    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationRequest request) {
        Notification notification = Notification.create(
            request.getTitle(),
            request.getMessage(),
            request.getType(),
            request.getUserId()
        );
        
        notificationProducer.sendNotification(notification);
        
        return ResponseEntity.ok(notification);
    }
    
    @PostMapping("/broadcast")
    public ResponseEntity<Notification> broadcastNotification(@RequestBody NotificationRequest request) {
        Notification notification = Notification.create(
            request.getTitle(),
            request.getMessage(),
            request.getType(),
            "broadcast"
        );
        
        notificationProducer.sendNotification(notification);
        
        return ResponseEntity.ok(notification);
    }
}

@lombok.Data
class NotificationRequest {
    private String title;
    private String message;
    private String type;
    private String userId;
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}
	public String getUserId() {
		// TODO Auto-generated method stub
		return userId;
	}
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
}
