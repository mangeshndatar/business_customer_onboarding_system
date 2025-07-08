package com.bank.onboarding.dto;

import com.bank.onboarding.enums.IndustryType;
import com.bank.onboarding.enums.LegalStructureType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ApplicationRequestDTO {
    
//    @NotBlank(message = "Legal name is required")
//    @Size(max = 255, message = "Legal name must not exceed 255 characters")
    private String legalName;
    
//    @NotNull(message = "Legal structure type is required")
    private LegalStructureType legalStructureType;
    
//    @NotBlank(message = "Country of incorporation is required")
    @Size(max = 100, message = "Country of incorporation must not exceed 100 characters")
    private String countryOfIncorporation;
    
//    @NotBlank(message = "Business registration number is required")
    @Size(max = 100, message = "Business registration number must not exceed 100 characters")
    private String businessRegistrationNumber;
    
    @Size(max = 100, message = "Tax identification number must not exceed 100 characters")
    private String taxIdentificationNumber;
    
//    @NotNull(message = "Industry type is required")
    private IndustryType industryType;
    
//    @NotBlank(message = "Primary contact person is required")
    @Size(max = 255, message = "Primary contact person must not exceed 255 characters")
    private String primaryContactPerson;
    
//    @NotBlank(message = "Contact email is required")
//    @Email(message = "Contact email must be valid")
    @Size(max = 255, message = "Contact email must not exceed 255 characters")
    private String contactEmail;
    
//    @DecimalMin(value = "0.0", inclusive = false, message = "Estimated annual turnover must be positive")
    private BigDecimal estimatedAnnualTurnover;
    
//    @DecimalMin(value = "0.0", inclusive = false, message = "Expected monthly transaction volume must be positive")
    private BigDecimal expectedMonthlyTransactionVolume;
    
//    @NotEmpty(message = "At least one director is required")
    @Valid
    private List<DirectorDTO> directors;
    
//    @NotEmpty(message = "At least one ultimate beneficial owner is required")
    @Valid
    private List<UboDTO> ultimateBeneficialOwners;

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public LegalStructureType getLegalStructureType() {
		return legalStructureType;
	}

	public void setLegalStructureType(LegalStructureType legalStructureType) {
		this.legalStructureType = legalStructureType;
	}

	public String getCountryOfIncorporation() {
		return countryOfIncorporation;
	}

	public void setCountryOfIncorporation(String countryOfIncorporation) {
		this.countryOfIncorporation = countryOfIncorporation;
	}

	public String getBusinessRegistrationNumber() {
		return businessRegistrationNumber;
	}

	public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
		this.businessRegistrationNumber = businessRegistrationNumber;
	}

	public String getTaxIdentificationNumber() {
		return taxIdentificationNumber;
	}

	public void setTaxIdentificationNumber(String taxIdentificationNumber) {
		this.taxIdentificationNumber = taxIdentificationNumber;
	}

	public IndustryType getIndustryType() {
		return industryType;
	}

	public void setIndustryType(IndustryType industryType) {
		this.industryType = industryType;
	}

	public String getPrimaryContactPerson() {
		return primaryContactPerson;
	}

	public void setPrimaryContactPerson(String primaryContactPerson) {
		this.primaryContactPerson = primaryContactPerson;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public BigDecimal getEstimatedAnnualTurnover() {
		return estimatedAnnualTurnover;
	}

	public void setEstimatedAnnualTurnover(BigDecimal estimatedAnnualTurnover) {
		this.estimatedAnnualTurnover = estimatedAnnualTurnover;
	}

	public BigDecimal getExpectedMonthlyTransactionVolume() {
		return expectedMonthlyTransactionVolume;
	}

	public void setExpectedMonthlyTransactionVolume(BigDecimal expectedMonthlyTransactionVolume) {
		this.expectedMonthlyTransactionVolume = expectedMonthlyTransactionVolume;
	}

	public List<DirectorDTO> getDirectors() {
		return directors;
	}

	public void setDirectors(List<DirectorDTO> directors) {
		this.directors = directors;
	}

	public List<UboDTO> getUltimateBeneficialOwners() {
		return ultimateBeneficialOwners;
	}

	public void setUltimateBeneficialOwners(List<UboDTO> ultimateBeneficialOwners) {
		this.ultimateBeneficialOwners = ultimateBeneficialOwners;
	}
    
    
}
