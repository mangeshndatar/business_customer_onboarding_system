package com.bank.onboarding.dto;

import com.bank.onboarding.enums.ApplicationStatus;

import lombok.Data;

@Data
public class ApplicationQueuePayload {
	// Default constructor (required by Jackson)
	private String applicationId;
	private ApplicationStatus status;

	public ApplicationQueuePayload() {
	}

	// Parameterized constructor (optional)
	public ApplicationQueuePayload(String applicationId, ApplicationStatus status) {
		this.applicationId = applicationId;
		this.status = status;
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
}