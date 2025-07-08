package com.bank.onboarding.service;

import com.bank.onboarding.entity.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    
    @Async
    public void notifyProcessingTeam(Application application) {
        // Simulate message queue notification
        System.out.printf("🔔 NOTIFICATION TO PROCESSING TEAM:");
        System.out.printf("   New application submitted: {}", application.getApplicationId());
        System.out.printf("   Legal Name: {}", application.getLegalName());
        System.out.printf("   Business Registration: {}", application.getBusinessRegistrationNumber());
        System.out.printf("   Contact Email: {}", application.getContactEmail());
        System.out.printf("   Status: {}", application.getStatus());
        System.out.printf("   ⏰ Submitted at: {}", application.getCreatedAt());
        System.out.printf("   👥 Processing team should review this application");
        System.out.printf("=====================================");
    }
    
    @Async
    public void notifyApplicant(Application application) {
        // Simulate message queue notification
        System.out.printf("📧 NOTIFICATION TO APPLICANT:");
        System.out.printf("   Application ID: {}", application.getApplicationId());
        System.out.printf("   Legal Name: {}", application.getLegalName());
        System.out.printf("   Contact Email: {}", application.getContactEmail());
        System.out.printf("   Status: {}", application.getStatus());
        System.out.printf("   📝 Processing Notes: {}", application.getProcessingNotes());
        System.out.printf("   ⏰ Processed at: {}", application.getProcessedAt());
        System.out.printf("   📬 Email sent to applicant about decision");
        System.out.printf("=====================================");
    }
}