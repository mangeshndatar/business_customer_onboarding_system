package com.bank.onboarding.repository;

import com.bank.onboarding.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    List<Document> findByApplicationId(Long applicationId);
    
    void deleteByApplicationId(Long applicationId);
}