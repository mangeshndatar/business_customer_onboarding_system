package com.onboarding.repository;
import com.onboarding.entity.BusinessApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusinessApplicationRepository extends JpaRepository<BusinessApplication, Long> {
    List<BusinessApplication> findByPersonaFlag(String flag);
}

