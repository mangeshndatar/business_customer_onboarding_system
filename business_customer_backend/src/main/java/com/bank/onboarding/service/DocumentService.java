package com.bank.onboarding.service;

import com.bank.onboarding.dto.DocumentDTO;
import com.bank.onboarding.entity.Application;
import com.bank.onboarding.entity.Document;
import com.bank.onboarding.exception.BusinessException;
import com.bank.onboarding.repository.ApplicationRepository;
import com.bank.onboarding.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentService {
    
    private  DocumentRepository documentRepository;
    private  ApplicationRepository applicationRepository;
    
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    
    public List<DocumentDTO> uploadDocuments(Long applicationId, List<MultipartFile> files) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BusinessException("Application not found"));
        
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new BusinessException("Could not create upload directory");
            }
        }
        
        List<Document> documents = files.stream()
                .map(file -> uploadSingleDocument(application, file))
                .collect(Collectors.toList());
        
        List<Document> savedDocuments = documentRepository.saveAll(documents);
        
        return savedDocuments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private Document uploadSingleDocument(Application application, MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("File is empty");
        }
        
        // Generate unique filename
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        try {
            // Save file to disk
            Path filePath = Paths.get(uploadDir, uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Create document entity
            Document document = new Document();
            document.setFileName(uniqueFileName);
            document.setOriginalFileName(originalFileName);
            document.setFilePath(filePath.toString());
            document.setFileSize(file.getSize());
            document.setContentType(file.getContentType());
            document.setApplication(application);
            
            return document;
            
        } catch (IOException e) {
            throw new BusinessException("Could not save file: " + originalFileName);
        }
    }
    
    @Transactional(readOnly = true)
    public List<DocumentDTO> getDocumentsByApplicationId(Long applicationId) {
        List<Document> documents = documentRepository.findByApplicationId(applicationId);
        return documents.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Resource downloadDocument(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new BusinessException("Document not found"));
        
        try {
            Path filePath = Paths.get(document.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new BusinessException("Could not read file: " + document.getFileName());
            }
        } catch (MalformedURLException e) {
            throw new BusinessException("Could not read file: " + document.getFileName());
        }
    }
    
    public void deleteDocument(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new BusinessException("Document not found"));
        
        try {
            // Delete file from disk
            Path filePath = Paths.get(document.getFilePath());
            Files.deleteIfExists(filePath);
            
            // Delete from database
            documentRepository.delete(document);
            
        } catch (IOException e) {
            throw new BusinessException("Could not delete file: " + document.getFileName());
        }
    }
    
    private DocumentDTO convertToDTO(Document document) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setFileName(document.getFileName());
        dto.setOriginalFileName(document.getOriginalFileName());
        dto.setFilePath(document.getFilePath());
        dto.setFileSize(document.getFileSize());
        dto.setContentType(document.getContentType());
        dto.setUploadedAt(document.getUploadedAt());
        return dto;
    }
}