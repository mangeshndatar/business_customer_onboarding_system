package com.onboarding.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "business_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "legal_structure")
    private String legalStructure;

    @Column(name = "country_of_incorporation")
    private String countryOfIncorporation;

    @Column(name = "business_reg_number")
    private String businessRegistrationNumber;

    @Column(name = "tax_id_number")
    private String taxIdNumber;

    @Column(name = "industry_type")
    private String industryType;

    @Column(name = "directors")
    private String directors; // JSON string for now

    @Column(name = "primary_contact")
    private String primaryContact;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "annual_turnover")
    private String estimatedAnnualTurnover;

    @Column(name = "monthly_txn_volume")
    private String expectedMonthlyTxnVolume;

    @Column(name = "ubos")
    private String ubos; // JSON string for now

    @Column(name = "document_paths")
    private String documentPaths;

    @Column(name = "persona_flag")
    private String personaFlag; // APPLICANT or PROCESSING_OFFICER

	public String getPersonaFlag() {
		return personaFlag;
	}

	public void setPersonaFlag(String personaFlag) {
		this.personaFlag = personaFlag;
	}
    
    
}
