package com.bank.onboarding.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
    private String type;
    private String applicationId;
    private Object data;
    private LocalDateTime timestamp;
    private String priority; // HIGH, NORMAL, LOW
    private WebSocketMessage(Builder builder) {
        this.type = builder.type;
        this.applicationId = builder.applicationId;
        this.data = builder.data;
        this.timestamp = builder.timestamp;
        this.priority = builder.priority;
    }

    // Static builder() method
    public static Builder builder() {
        return new Builder();
    }

    // Builder Class
    public static class Builder {
        private String type;
        private String applicationId;
        private Object data;
        private LocalDateTime timestamp;
        private String priority;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public WebSocketMessage build() {
            return new WebSocketMessage(this);
        }
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Object getData() {
        return data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPriority() {
        return priority;
    }
    
}