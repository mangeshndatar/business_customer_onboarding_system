package com.bank.onboarding.repository;

import com.bank.onboarding.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    
    List<Director> findByApplicationId(Long applicationId);
    
    void deleteByApplicationId(Long applicationId);
}
