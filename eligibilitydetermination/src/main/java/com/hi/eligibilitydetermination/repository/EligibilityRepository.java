package com.hi.eligibilitydetermination.repository;

import com.hi.eligibilitydetermination.entity.EligibilityDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EligibilityRepository extends JpaRepository<EligibilityDetailsEntity, Long> {

}
