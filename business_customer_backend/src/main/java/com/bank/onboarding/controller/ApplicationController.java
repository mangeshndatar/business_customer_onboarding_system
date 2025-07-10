package com.bank.onboarding.controller;

import com.bank.onboarding.dto.ApplicationRequestDTO;
import com.bank.onboarding.dto.ApplicationResponseDTO;
import com.bank.onboarding.dto.DocumentDTO;
import com.bank.onboarding.entity.Notification;
import com.bank.onboarding.enums.ApplicationStatus;
import com.bank.onboarding.service.ApplicationService;
import com.bank.onboarding.service.DocumentService;
import com.bank.onboarding.service.NotificationProducer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApplicationController {
    
	@Autowired
    private ApplicationService applicationService;
	@Autowired
    private DocumentService documentService;
	@Autowired
    private  NotificationProducer notificationProducer;
	
    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> submitApplication(
            @Valid @RequestBody ApplicationRequestDTO requestDTO) {
    	System.out.println("requestDTO ::"+requestDTO);
        ApplicationResponseDTO response = applicationService.submitApplication(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getApplication(@PathVariable Long id) {
        ApplicationResponseDTO response = applicationService.getApplicationById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/application-id/{applicationId}")
    public ResponseEntity<ApplicationResponseDTO> getApplicationByApplicationId(
            @PathVariable String applicationId) {
        ApplicationResponseDTO response = applicationService.getApplicationByApplicationId(applicationId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<Page<ApplicationResponseDTO>> getAllApplications(
            @PageableDefault(size = 30) Pageable pageable) {
        Page<ApplicationResponseDTO> applications = applicationService.getAllApplications(pageable);
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ApplicationResponseDTO>> getApplicationsByStatus(
            @PathVariable ApplicationStatus status,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ApplicationResponseDTO> applications = applicationService.getApplicationsByStatus(status, pageable);
        return ResponseEntity.ok(applications);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationRequestDTO requestDTO) {
        ApplicationResponseDTO response = applicationService.updateApplication(id, requestDTO);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/documents")
    public ResponseEntity<List<DocumentDTO>> uploadDocuments(
            @PathVariable String id,
            @RequestParam("files") List<MultipartFile> files) {
        List<DocumentDTO> documents = documentService.uploadDocuments(id, files);
        return ResponseEntity.ok(documents);
    }
    
    @GetMapping("/{id}/documents")
    public ResponseEntity<List<DocumentDTO>> getDocuments(@PathVariable Long id) {
        List<DocumentDTO> documents = documentService.getDocumentsByApplicationId(id);
        return ResponseEntity.ok(documents);
    }
    
    @GetMapping("/documents/{documentId}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) {
        Resource resource = documentService.downloadDocument(documentId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}