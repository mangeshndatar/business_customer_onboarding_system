package com.bank.onboarding.dto;
import com.bank.onboarding.enums.ApplicationStatus;
import com.bank.onboarding.enums.IndustryType;
import com.bank.onboarding.enums.LegalStructureType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApplicationResponseDTO {
    private Long id;
    private String applicationId;
    private String legalName;
    private LegalStructureType legalStructureType;
    private String countryOfIncorporation;
    private String businessRegistrationNumber;
    private String taxIdentificationNumber;
    private IndustryType industryType;
    private String primaryContactPerson;
    private String contactEmail;
    private BigDecimal estimatedAnnualTurnover;
    private BigDecimal expectedMonthlyTransactionVolume;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime processedAt;
    private String processingNotes;
    private List<DirectorDTO> directors;
    private List<UboDTO> ultimateBeneficialOwners;
    private List<DocumentDTO> documents;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
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
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public LocalDateTime getProcessedAt() {
		return processedAt;
	}
	public void setProcessedAt(LocalDateTime processedAt) {
		this.processedAt = processedAt;
	}
	public String getProcessingNotes() {
		return processingNotes;
	}
	public void setProcessingNotes(String processingNotes) {
		this.processingNotes = processingNotes;
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
	public List<DocumentDTO> getDocuments() {
		return documents;
	}
	public void setDocuments(List<DocumentDTO> documents) {
		this.documents = documents;
	}
    
    
}