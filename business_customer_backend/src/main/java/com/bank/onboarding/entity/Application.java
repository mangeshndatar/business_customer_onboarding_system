package com.bank.onboarding.entity;

import com.bank.onboarding.enums.ApplicationStatus;
import com.bank.onboarding.enums.IndustryType;
import com.bank.onboarding.enums.LegalStructureType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "application_id", unique = true, nullable = false)
    private String applicationId;
    
    @Column(name = "legal_name", nullable = false)
    private String legalName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "legal_structure_type", nullable = false)
    private LegalStructureType legalStructureType;
    
    @Column(name = "country_of_incorporation", nullable = false)
    private String countryOfIncorporation;
    
    @Column(name = "business_registration_number", nullable = false)
    private String businessRegistrationNumber;
    
    @Column(name = "tax_identification_number")
    private String taxIdentificationNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "industry_type", nullable = false)
    private IndustryType industryType;
    
    @Column(name = "primary_contact_person", nullable = false)
    private String primaryContactPerson;
    
    @Column(name = "contact_email", nullable = false)
    private String contactEmail;
    
    @Column(name = "estimated_annual_turnover")
    private BigDecimal estimatedAnnualTurnover;
    
    @Column(name = "expected_monthly_transaction_volume")
    private BigDecimal expectedMonthlyTransactionVolume;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
    
    @Column(name = "processing_notes")
    private String processingNotes;
    
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Director> directors;
    
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UltimateBeneficialOwner> ultimateBeneficialOwners;
    
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = ApplicationStatus.PENDING;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

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

	public List<Director> getDirectors() {
		return directors;
	}

	public void setDirectors(List<Director> directors) {
		this.directors = directors;
	}

	public List<UltimateBeneficialOwner> getUltimateBeneficialOwners() {
		return ultimateBeneficialOwners;
	}

	public void setUltimateBeneficialOwners(List<UltimateBeneficialOwner> ultimateBeneficialOwners) {
		this.ultimateBeneficialOwners = ultimateBeneficialOwners;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
    
    
}