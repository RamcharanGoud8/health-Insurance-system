package com.hi.benifitIssuance.repository;

import com.hi.benifitIssuance.entity.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenefitRepository extends JpaRepository<Benefit, Integer> {

    Benefit findByCaseId(Integer caseId);
}
