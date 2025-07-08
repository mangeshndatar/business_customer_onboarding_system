package com.bank.onboarding.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DirectorDTO {
    
//    @NotBlank(message = "Director name is required")
    @Size(max = 255, message = "Director name must not exceed 255 characters")
    private String name;
    
    @NotBlank(message = "National ID/Passport is required")
    @Size(max = 100, message = "National ID/Passport must not exceed 100 characters")
    private String nationalIdPassport;
    
    @NotBlank(message = "Country of residence is required")
    @Size(max = 100, message = "Country of residence must not exceed 100 characters")
    private String countryOfResidence;

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
    
    
}
