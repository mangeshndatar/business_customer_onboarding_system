package com.bank.onboarding.repository;

import com.bank.onboarding.entity.Application;
import com.bank.onboarding.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    Optional<Application> findByApplicationId(String applicationId);
    
    List<Application> findByStatus(ApplicationStatus status);
    
    Page<Application> findByStatus(ApplicationStatus status, Pageable pageable);
    
    @Query("SELECT a FROM Application a WHERE a.status IN :statuses ORDER BY a.createdAt DESC")
    Page<Application> findByStatusIn(List<ApplicationStatus> statuses, Pageable pageable);
    
    boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);
    
    boolean existsByContactEmail(String contactEmail);
}