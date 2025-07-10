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
public class DashboardUpdate {
    private String updateType;
    private Object data;
    private LocalDateTime timestamp;
    private DashboardUpdate(Builder builder) {
        this.updateType = builder.updateType;
        this.data = builder.data;
        this.timestamp = builder.timestamp;
    }

    // Static builder() method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String updateType;
        private Object data;
        private LocalDateTime timestamp;

        public Builder updateType(String updateType) {
            this.updateType = updateType;
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

        public DashboardUpdate build() {
            return new DashboardUpdate(this);
        }
    }

    // Getters
    public String getUpdateType() {
        return updateType;
    }

    public Object getData() {
        return data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
