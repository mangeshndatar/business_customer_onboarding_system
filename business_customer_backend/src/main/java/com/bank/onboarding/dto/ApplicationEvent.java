package com.bank.onboarding.dto;

import com.bank.onboarding.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationEvent {
    private String applicationId;
    private ApplicationStatus status;
    
    // Default constructor for Jackson
    public ApplicationEvent() {}
    
    // Constructor for manual creation
    public ApplicationEvent(String applicationId, ApplicationStatus status) {
        this.applicationId = applicationId;
        this.status = status;
    }
    
    // Jackson constructor (handles String to Enum conversion)
    @JsonCreator
    public ApplicationEvent(
            @JsonProperty("applicationId") String applicationId,
            @JsonProperty("status") String status) {
        this.applicationId = applicationId;
        this.status = ApplicationStatus.valueOf(status);
    }
    
    // Getters and setters
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
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String applicationId;
        private ApplicationStatus status;
        
        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }
        
        public Builder status(ApplicationStatus status) {
            this.status = status;
            return this;
        }
        
        public ApplicationEvent build() {
            return new ApplicationEvent(applicationId, status);
        }
    }
}