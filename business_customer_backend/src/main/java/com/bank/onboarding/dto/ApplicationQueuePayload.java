package com.bank.onboarding.dto;


import com.bank.onboarding.entity.Application;
import com.bank.onboarding.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApplicationQueuePayload {
	  private String applicationId;
	  private String legalName;
	  private String legalStructure;
	  private String countryOfIncorporation;
	  private String registrationNumber;
	  private String taxId;
	  private String industryType;
	  private String contactPerson;
	  private String contactEmail;
	  private String annualTurnover;
	  private String monthlyVolume;
	  private ApplicationStatus appStatus;
	  
	  public static ApplicationQueuePayload mapTo(Application a) {
		  return ApplicationQueuePayload.builder().applicationId(a.getApplicationId())
				  .legalName(a.getLegalName())
				  .countryOfIncorporation(a.getCountryOfIncorporation())
				  .contactEmail(a.getContactEmail())
				  .appStatus(a.getStatus())
				  .build();
	  }
	  private ApplicationQueuePayload(Builder builder) {
	        this.applicationId = builder.applicationId;
	        this.legalName = builder.legalName;
	        this.legalStructure = builder.legalStructure;
	        this.countryOfIncorporation = builder.countryOfIncorporation;
	        this.registrationNumber = builder.registrationNumber;
	        this.taxId = builder.taxId;
	        this.industryType = builder.industryType;
	        this.contactPerson = builder.contactPerson;
	        this.contactEmail = builder.contactEmail;
	        this.annualTurnover = builder.annualTurnover;
	        this.monthlyVolume = builder.monthlyVolume;
	        this.appStatus = builder.appStatus;
	    }

	    // Static builder() method
	    public static Builder builder() {
	        return new Builder();
	    }

	    // Builder class
	    public static class Builder {
	        private String applicationId;
	        private String legalName;
	        private String legalStructure;
	        private String countryOfIncorporation;
	        private String registrationNumber;
	        private String taxId;
	        private String industryType;
	        private String contactPerson;
	        private String contactEmail;
	        private String annualTurnover;
	        private String monthlyVolume;
	        private ApplicationStatus appStatus;

	        public Builder applicationId(String applicationId) {
	            this.applicationId = applicationId;
	            return this;
	        }

	        public Builder legalName(String legalName) {
	            this.legalName = legalName;
	            return this;
	        }

	        public Builder legalStructure(String legalStructure) {
	            this.legalStructure = legalStructure;
	            return this;
	        }

	        public Builder countryOfIncorporation(String countryOfIncorporation) {
	            this.countryOfIncorporation = countryOfIncorporation;
	            return this;
	        }

	        public Builder registrationNumber(String registrationNumber) {
	            this.registrationNumber = registrationNumber;
	            return this;
	        }

	        public Builder taxId(String taxId) {
	            this.taxId = taxId;
	            return this;
	        }

	        public Builder industryType(String industryType) {
	            this.industryType = industryType;
	            return this;
	        }

	        public Builder contactPerson(String contactPerson) {
	            this.contactPerson = contactPerson;
	            return this;
	        }

	        public Builder contactEmail(String contactEmail) {
	            this.contactEmail = contactEmail;
	            return this;
	        }

	        public Builder annualTurnover(String annualTurnover) {
	            this.annualTurnover = annualTurnover;
	            return this;
	        }

	        public Builder monthlyVolume(String monthlyVolume) {
	            this.monthlyVolume = monthlyVolume;
	            return this;
	        }

	        public Builder appStatus(ApplicationStatus appStatus) {
	            this.appStatus = appStatus;
	            return this;
	        }

	        public ApplicationQueuePayload build() {
	            return new ApplicationQueuePayload(this);
	        }
	    }

	    // Getters (optional if not using Lombok)
	    public String getApplicationId() {
	        return applicationId;
	    }

	    public String getLegalName() {
	        return legalName;
	    }

	    public String getLegalStructure() {
	        return legalStructure;
	    }

	    public String getCountryOfIncorporation() {
	        return countryOfIncorporation;
	    }

	    public String getRegistrationNumber() {
	        return registrationNumber;
	    }

	    public String getTaxId() {
	        return taxId;
	    }

	    public String getIndustryType() {
	        return industryType;
	    }

	    public String getContactPerson() {
	        return contactPerson;
	    }

	    public String getContactEmail() {
	        return contactEmail;
	    }

	    public String getAnnualTurnover() {
	        return annualTurnover;
	    }

	    public String getMonthlyVolume() {
	        return monthlyVolume;
	    }

	    public ApplicationStatus getAppStatus() {
	        return appStatus;
	    }
}
