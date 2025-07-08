package com.bank.onboarding.dto;

import com.bank.onboarding.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcessingDecisionDTO {
    
    @NotNull(message = "Decision is required")
    private ApplicationStatus decision;
    
    private String notes;

	public ApplicationStatus getDecision() {
		return decision;
	}

	public void setDecision(ApplicationStatus decision) {
		this.decision = decision;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
    
    
    
}