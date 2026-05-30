package com.hi.benifitIssuance.service;


import com.hi.benifitIssuance.dto.BenefitRequest;
import com.hi.benifitIssuance.dto.BenefitResponse;
import com.hi.benifitIssuance.entity.Benefit;

import java.util.List;

public interface BenefitService {

    BenefitResponse issueBenefit(BenefitRequest request);

    Benefit getBenefitByCaseId(Integer caseId);

    List<Benefit> getAllBenefits();

    String updatePaymentStatus(Integer benefitId, String status);

    String terminateBenefit(Integer benefitId);
}