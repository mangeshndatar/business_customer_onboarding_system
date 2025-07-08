package com.bank.onboarding.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * 
 */
@Entity
@Table(name = "ultimate_beneficial_owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UltimateBeneficialOwner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "national_id_passport", nullable = false)
    private String nationalIdPassport;
    
    @Column(name = "country_of_residence", nullable = false)
    private String countryOfResidence;
    
    @Column(name = "ownership_percentage", nullable = false)
    private BigDecimal ownershipPercentage;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
    
    
    
}
