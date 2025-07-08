package com.bank.onboarding.repository;

import com.bank.onboarding.entity.UltimateBeneficialOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UboRepository extends JpaRepository<UltimateBeneficialOwner, Long> {
    
    List<UltimateBeneficialOwner> findByApplicationId(Long applicationId);
    
    void deleteByApplicationId(Long applicationId);
}