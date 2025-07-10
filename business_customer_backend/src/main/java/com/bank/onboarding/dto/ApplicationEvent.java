package com.bank.onboarding.dto;

import java.time.LocalDateTime;

import com.bank.onboarding.enums.ApplicationStatus;

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
    private String eventType; // SUBMITTED, APPROVED, REJECTED, PENDING_REVIEW
    private LocalDateTime timestamp;
    private String status;
    private ApplicationEvent(Builder builder) {
        this.applicationId = builder.applicationId;
        this.eventType = builder.eventType;
        this.timestamp = builder.timestamp;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String applicationId;
        private String eventType;
        private LocalDateTime timestamp;
        private ApplicationStatus status;
        
        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ApplicationEvent build() {
            return new ApplicationEvent(this);
        }

		public Builder status(ApplicationStatus applicationStatus) {
			// TODO Auto-generated method stub
			this.status = applicationStatus;
			return this;
		}
    }

    // Getters
    public String getApplicationId() {
        return applicationId;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}