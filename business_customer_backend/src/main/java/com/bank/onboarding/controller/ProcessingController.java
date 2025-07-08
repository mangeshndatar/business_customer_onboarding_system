package com.bank.onboarding.controller;

import com.bank.onboarding.dto.ApplicationResponseDTO;
import com.bank.onboarding.dto.ProcessingDecisionDTO;
import com.bank.onboarding.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processing")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProcessingController {
    
	@Autowired
    private ApplicationService applicationService;
    
    @PostMapping("/applications/{id}/process")
    public ResponseEntity<ApplicationResponseDTO> processApplication(
            @PathVariable Long id,
            @Valid @RequestBody ProcessingDecisionDTO decisionDTO) {
        ApplicationResponseDTO response = applicationService.processApplication(id, decisionDTO);
        return ResponseEntity.ok(response);
    }
}