package com.bank.onboarding.service;

import com.bank.onboarding.dto.*;
import com.bank.onboarding.entity.Application;
import com.bank.onboarding.entity.Director;
import com.bank.onboarding.entity.UltimateBeneficialOwner;
import com.bank.onboarding.enums.ApplicationStatus;
import com.bank.onboarding.exception.BusinessException;
import com.bank.onboarding.kafka.KafkaProducerService;
import com.bank.onboarding.repository.ApplicationRepository;
import com.bank.onboarding.repository.DirectorRepository;
import com.bank.onboarding.repository.UboRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {
    
	@Autowired
    private  ApplicationRepository applicationRepository;
	@Autowired
    private  DirectorRepository directorRepository;
	@Autowired
    private  UboRepository uboRepository;
	@Autowired
    private  NotificationService notificationService;
	


	@Autowired
	private  KafkaProducerService kafkaProducerService;
	
    public ApplicationResponseDTO submitApplication(ApplicationRequestDTO requestDTO) {
        // Check for duplicate business registration number
        if (applicationRepository.existsByBusinessRegistrationNumber(requestDTO.getBusinessRegistrationNumber())) {
            throw new BusinessException("Business registration number already exists");
        }
        
        // Check for duplicate contact email
        if (applicationRepository.existsByContactEmail(requestDTO.getContactEmail())) {
            throw new BusinessException("Contact email already exists");
        }
        
        // Create application entity
        Application application = new Application();
        application.setApplicationId(generateApplicationId());
        application.setLegalName(requestDTO.getLegalName());
        application.setLegalStructureType(requestDTO.getLegalStructureType());
        application.setCountryOfIncorporation(requestDTO.getCountryOfIncorporation());
        application.setBusinessRegistrationNumber(requestDTO.getBusinessRegistrationNumber());
        application.setTaxIdentificationNumber(requestDTO.getTaxIdentificationNumber());
        application.setIndustryType(requestDTO.getIndustryType());
        application.setPrimaryContactPerson(requestDTO.getPrimaryContactPerson());
        application.setContactEmail(requestDTO.getContactEmail());
        application.setEstimatedAnnualTurnover(requestDTO.getEstimatedAnnualTurnover());
        application.setExpectedMonthlyTransactionVolume(requestDTO.getExpectedMonthlyTransactionVolume());
        application.setStatus(ApplicationStatus.PENDING);
        
        // Save application
        Application savedApplication = applicationRepository.save(application);
        
        // Save directors
        List<Director> directors = requestDTO.getDirectors().stream()
                .map(directorDTO -> {
                    Director director = new Director();
                    director.setName(directorDTO.getName());
                    director.setNationalIdPassport(directorDTO.getNationalIdPassport());
                    director.setCountryOfResidence(directorDTO.getCountryOfResidence());
                    director.setApplication(savedApplication);
                    return director;
                })
                .collect(Collectors.toList());
        
        directorRepository.saveAll(directors);
        
        // Save UBOs
        List<UltimateBeneficialOwner> ubos = requestDTO.getUltimateBeneficialOwners().stream()
                .map(uboDTO -> {
                    UltimateBeneficialOwner ubo = new UltimateBeneficialOwner();
                    ubo.setName(uboDTO.getName());
                    ubo.setNationalIdPassport(uboDTO.getNationalIdPassport());
                    ubo.setCountryOfResidence(uboDTO.getCountryOfResidence());
                    ubo.setOwnershipPercentage(uboDTO.getOwnershipPercentage());
                    ubo.setApplication(savedApplication);
                    return ubo;
                })
                .collect(Collectors.toList());
        
        uboRepository.saveAll(ubos);
        
        // Send notification to processing team
        notificationService.notifyProcessingTeam(savedApplication);
        kafkaProducerService.sendApplication(ApplicationQueuePayload.mapTo(application));
		
		kafkaProducerService.sendNotification(ApplicationEvent.builder()
				.applicationId(application.getApplicationId())
				.status(application.getStatus())
				.build());
        
        return convertToResponseDTO(savedApplication);
    }
    
    @Transactional(readOnly = true)
    public ApplicationResponseDTO getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Application not found"));
        
        return convertToResponseDTO(application);
    }
    
    @Transactional(readOnly = true)
    public ApplicationResponseDTO getApplicationByApplicationId(String applicationId) {
        Application application = applicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new BusinessException("Application not found"));
        
        return convertToResponseDTO(application);
    }
    
    @Transactional(readOnly = true)
    public Page<ApplicationResponseDTO> getAllApplications(Pageable pageable) {
        return applicationRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }
    
    @Transactional(readOnly = true)
    public Page<ApplicationResponseDTO> getApplicationsByStatus(ApplicationStatus status, Pageable pageable) {
        return applicationRepository.findByStatus(status, pageable)
                .map(this::convertToResponseDTO);
    }
    
    public ApplicationResponseDTO processApplication(Long applicationId, ProcessingDecisionDTO decisionDTO) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException("Application not found"));
        
        application.setStatus(decisionDTO.getDecision());
        application.setProcessingNotes(decisionDTO.getNotes());
        application.setProcessedAt(LocalDateTime.now());
        
        Application savedApplication = applicationRepository.save(application);
        
        // Notify applicant about the decision
        notificationService.notifyApplicant(savedApplication);
        
        return convertToResponseDTO(savedApplication);
    }
    
    public ApplicationResponseDTO updateApplication(Long applicationId, ApplicationRequestDTO requestDTO) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException("Application not found"));
        
        // Update application fields
        application.setLegalName(requestDTO.getLegalName());
        application.setLegalStructureType(requestDTO.getLegalStructureType());
        application.setCountryOfIncorporation(requestDTO.getCountryOfIncorporation());
        application.setBusinessRegistrationNumber(requestDTO.getBusinessRegistrationNumber());
        application.setTaxIdentificationNumber(requestDTO.getTaxIdentificationNumber());
        application.setIndustryType(requestDTO.getIndustryType());
        application.setPrimaryContactPerson(requestDTO.getPrimaryContactPerson());
        application.setContactEmail(requestDTO.getContactEmail());
        application.setEstimatedAnnualTurnover(requestDTO.getEstimatedAnnualTurnover());
        application.setExpectedMonthlyTransactionVolume(requestDTO.getExpectedMonthlyTransactionVolume());
        
        // Update directors
        directorRepository.deleteByApplicationId(applicationId);
        List<Director> directors = requestDTO.getDirectors().stream()
                .map(directorDTO -> {
                    Director director = new Director();
                    director.setName(directorDTO.getName());
                    director.setNationalIdPassport(directorDTO.getNationalIdPassport());
                    director.setCountryOfResidence(directorDTO.getCountryOfResidence());
                    director.setApplication(application);
                    return director;
                })
                .collect(Collectors.toList());
        
        directorRepository.saveAll(directors);
        
        // Update UBOs
        uboRepository.deleteByApplicationId(applicationId);
        List<UltimateBeneficialOwner> ubos = requestDTO.getUltimateBeneficialOwners().stream()
                .map(uboDTO -> {
                    UltimateBeneficialOwner ubo = new UltimateBeneficialOwner();
                    ubo.setName(uboDTO.getName());
                    ubo.setNationalIdPassport(uboDTO.getNationalIdPassport());
                    ubo.setCountryOfResidence(uboDTO.getCountryOfResidence());
                    ubo.setOwnershipPercentage(uboDTO.getOwnershipPercentage());
                    ubo.setApplication(application);
                    return ubo;
                })
                .collect(Collectors.toList());
        
        uboRepository.saveAll(ubos);
        
        Application savedApplication = applicationRepository.save(application);
        
        return convertToResponseDTO(savedApplication);
    }
    
    private String generateApplicationId() {
        return "APP-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private ApplicationResponseDTO convertToResponseDTO(Application application) {
        ApplicationResponseDTO responseDTO = new ApplicationResponseDTO();
        responseDTO.setId(application.getId());
        responseDTO.setApplicationId(application.getApplicationId());
        responseDTO.setLegalName(application.getLegalName());
        responseDTO.setLegalStructureType(application.getLegalStructureType());
        responseDTO.setCountryOfIncorporation(application.getCountryOfIncorporation());
        responseDTO.setBusinessRegistrationNumber(application.getBusinessRegistrationNumber());
        responseDTO.setTaxIdentificationNumber(application.getTaxIdentificationNumber());
        responseDTO.setIndustryType(application.getIndustryType());
        responseDTO.setPrimaryContactPerson(application.getPrimaryContactPerson());
        responseDTO.setContactEmail(application.getContactEmail());
        responseDTO.setEstimatedAnnualTurnover(application.getEstimatedAnnualTurnover());
        responseDTO.setExpectedMonthlyTransactionVolume(application.getExpectedMonthlyTransactionVolume());
        responseDTO.setStatus(application.getStatus());
        responseDTO.setCreatedAt(application.getCreatedAt());
        responseDTO.setUpdatedAt(application.getUpdatedAt());
        responseDTO.setProcessedAt(application.getProcessedAt());
        responseDTO.setProcessingNotes(application.getProcessingNotes());
        
        // Convert directors
        if (application.getDirectors() != null) {
            List<DirectorDTO> directorDTOs = application.getDirectors().stream()
                    .map(director -> {
                        DirectorDTO directorDTO = new DirectorDTO();
                        directorDTO.setName(director.getName());
                        directorDTO.setNationalIdPassport(director.getNationalIdPassport());
                        directorDTO.setCountryOfResidence(director.getCountryOfResidence());
                        return directorDTO;
                    })
                    .collect(Collectors.toList());
            responseDTO.setDirectors(directorDTOs);
        }
        
        // Convert UBOs
        if (application.getUltimateBeneficialOwners() != null) {
            List<UboDTO> uboDTOs = application.getUltimateBeneficialOwners().stream()
                    .map(ubo -> {
                        UboDTO uboDTO = new UboDTO();
                        uboDTO.setName(ubo.getName());
                        uboDTO.setNationalIdPassport(ubo.getNationalIdPassport());
                        uboDTO.setCountryOfResidence(ubo.getCountryOfResidence());
                        uboDTO.setOwnershipPercentage(ubo.getOwnershipPercentage());
                        return uboDTO;
                    })
                    .collect(Collectors.toList());
            responseDTO.setUltimateBeneficialOwners(uboDTOs);
        }
        
        // Convert documents
        if (application.getDocuments() != null) {
            List<DocumentDTO> documentDTOs = application.getDocuments().stream()
                    .map(document -> {
                        DocumentDTO documentDTO = new DocumentDTO();
                        documentDTO.setId(document.getId());
                        documentDTO.setFileName(document.getFileName());
                        documentDTO.setOriginalFileName(document.getOriginalFileName());
                        documentDTO.setFilePath(document.getFilePath());
                        documentDTO.setFileSize(document.getFileSize());
                        documentDTO.setContentType(document.getContentType());
                        documentDTO.setUploadedAt(document.getUploadedAt());
                        return documentDTO;
                    })
                    .collect(Collectors.toList());
            responseDTO.setDocuments(documentDTOs);
        }
        
        return responseDTO;
    }
}