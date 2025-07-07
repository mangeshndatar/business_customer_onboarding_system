package com.onboarding.service;


import com.onboarding.entity.BusinessApplication;
import com.onboarding.repository.BusinessApplicationRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessApplicationService {

	@Autowired
    private BusinessApplicationRepository repository;

    public BusinessApplication submitApplication(BusinessApplication application) {
        application.setPersonaFlag("APPLICANT");
        return repository.save(application);
    }

    public List<BusinessApplication> getAllApplicationsForProcessing() {
        return repository.findByPersonaFlag("APPLICANT");
    }

    public BusinessApplication updateApplicationForProcessing(BusinessApplication updatedApplication) {
        updatedApplication.setPersonaFlag("PROCESSING_OFFICER");
        return repository.save(updatedApplication);
    }

    public BusinessApplication getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
