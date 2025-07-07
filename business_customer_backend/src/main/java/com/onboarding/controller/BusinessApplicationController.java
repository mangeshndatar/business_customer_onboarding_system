package com.onboarding.controller;


import com.onboarding.entity.BusinessApplication;
import com.onboarding.service.BusinessApplicationService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/applications")
public class BusinessApplicationController {
	
    @Autowired
    private BusinessApplicationService service;

    @PostMapping("/submit")
    public BusinessApplication submitApplication(@RequestBody BusinessApplication application) {
        return service.submitApplication(application);
    }

    @GetMapping("/pending")
    public List<BusinessApplication> getAllPendingApplications() {
        return service.getAllApplicationsForProcessing();
    }

    @GetMapping("/{id}")
    public BusinessApplication getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/process")
    public BusinessApplication processApplication(@RequestBody BusinessApplication application) {
        return service.updateApplicationForProcessing(application);
    }
}

