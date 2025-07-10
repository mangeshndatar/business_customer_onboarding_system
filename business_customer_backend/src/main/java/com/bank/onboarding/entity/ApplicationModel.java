package com.bank.onboarding.entity;


import java.time.LocalDateTime;

import com.bank.onboarding.enums.ApplicationStatus;

public class ApplicationModel {
    private String applicationId;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private String userInfo;

    public ApplicationModel() {
    }

    public ApplicationModel(String applicationId, ApplicationStatus status, LocalDateTime createdAt, String userInfo) {
        this.applicationId = applicationId;
        this.status = status;
        this.createdAt = createdAt;
        this.userInfo = userInfo;
    }

    // Getters and Setters
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "ApplicationModel{" +
                "applicationId='" + applicationId + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", userInfo='" + userInfo + '\'' +
                '}';
    }
}