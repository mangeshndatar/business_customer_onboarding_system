package com.bank.onboarding.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UboDTO {
    
    @NotBlank(message = "UBO name is required")
    @Size(max = 255, message = "UBO name must not exceed 255 characters")
    private String name;
    
    @NotBlank(message = "National ID/Passport is required")
    @Size(max = 100, message = "National ID/Passport must not exceed 100 characters")
    private String nationalIdPassport;
    
    @NotBlank(message = "Country of residence is required")
    @Size(max = 100, message = "Country of residence must not exceed 100 characters")
    private String countryOfResidence;
    
    @NotNull(message = "Ownership percentage is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Ownership percentage must be positive")
    @DecimalMax(value = "100.0", message = "Ownership percentage must not exceed 100%")
    private BigDecimal ownershipPercentage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationalIdPassport() {
		return nationalIdPassport;
	}

	public void setNationalIdPassport(String nationalIdPassport) {
		this.nationalIdPassport = nationalIdPassport;
	}

	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	public BigDecimal getOwnershipPercentage() {
		return ownershipPercentage;
	}

	public void setOwnershipPercentage(BigDecimal ownershipPercentage) {
		this.ownershipPercentage = ownershipPercentage;
	}
    
    
}