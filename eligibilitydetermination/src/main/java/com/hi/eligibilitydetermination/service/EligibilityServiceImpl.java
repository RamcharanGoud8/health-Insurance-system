package com.hi.eligibilitydetermination.service;

import com.hi.eligibilitydetermination.dto.BenefitRequestDTO;
import com.hi.eligibilitydetermination.dto.CorrespondenceRequestDTO;
import com.hi.eligibilitydetermination.dto.EligibilityRequest;
import com.hi.eligibilitydetermination.dto.EligibilityResponse;
import com.hi.eligibilitydetermination.entity.EligibilityDetailsEntity;
import com.hi.eligibilitydetermination.factory.PlanFactory;
import com.hi.eligibilitydetermination.feign.BenefitFeignClient;
import com.hi.eligibilitydetermination.feign.CorrespondenceFeignClient;
import com.hi.eligibilitydetermination.repository.EligibilityRepository;
import com.hi.eligibilitydetermination.strategy.PlanStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EligibilityServiceImpl implements EligibilityService {

    private static final Logger log = LoggerFactory.getLogger(EligibilityServiceImpl.class);

    private final PlanFactory planFactory;
    private final EligibilityRepository repository;

    @Autowired
    private BenefitFeignClient benefitFeignClient;

    @Autowired
    private CorrespondenceFeignClient correspondenceFeignClient;

    public EligibilityServiceImpl(PlanFactory planFactory, EligibilityRepository repository) {
        this.planFactory = planFactory;
        this.repository = repository;
    }

    @Override
    public EligibilityResponse determineEligibility(EligibilityRequest request) {
        log.info("Starting eligibility determination: caseId={}, planName={}, income={}, employmentStatus={}",
                request.getCaseId(), request.getPlanName(),
                request.getIncome(), request.getEmploymentStatus());

        // Step 1 — Run eligibility rules via strategy pattern
        PlanStrategy strategy = planFactory.getPlan(request.getPlanName());
        log.debug("Plan strategy resolved: caseId={}, strategy={}", request.getCaseId(), strategy.getClass().getSimpleName());

        EligibilityResponse response = strategy.determineEligibility(request);
        log.info("Eligibility result: caseId={}, planName={}, planStatus={}, benefitAmount={}, denialReason={}",
                request.getCaseId(), request.getPlanName(),
                response.getPlanStatus(), response.getBenefitAmount(), response.getDenialReason());

        // Step 2 — Persist result
        EligibilityDetailsEntity entity = new EligibilityDetailsEntity();
        BeanUtils.copyProperties(response, entity);
        entity.setCaseId(request.getCaseId());
        entity.setPlanName(request.getPlanName());
        entity.setCreatedDate(LocalDate.now());
        repository.save(entity);
        log.info("Eligibility result saved to DB: caseId={}, planStatus={}", request.getCaseId(), response.getPlanStatus());

        // Step 3 — If APPROVED, trigger benefit issuance
        if ("APPROVED".equals(response.getPlanStatus())) {
            log.info("Plan approved — calling benefit issuance service: caseId={}, planName={}, benefitAmount={}",
                    request.getCaseId(), request.getPlanName(), response.getBenefitAmount());
            try {
                BenefitRequestDTO benefitRequest = new BenefitRequestDTO();
                benefitRequest.setCaseId(request.getCaseId());
                benefitRequest.setPlanName(request.getPlanName());
                benefitRequest.setBenefitAmount(response.getBenefitAmount());
                benefitRequest.setCitizenName("Citizen-" + request.getCaseId());

                benefitFeignClient.issueBenefit(benefitRequest);
                log.info("Benefit issuance service called successfully: caseId={}", request.getCaseId());
            } catch (Exception e) {
                log.error("Benefit issuance service call failed: caseId={}, error={}", request.getCaseId(), e.getMessage(), e);
            }
        } else {
            log.info("Plan not approved — skipping benefit issuance: caseId={}, planStatus={}, denialReason={}",
                    request.getCaseId(), response.getPlanStatus(), response.getDenialReason());
        }

        // Step 4 — Always generate correspondence notice
        log.info("Calling correspondence service for notice generation: caseId={}, planStatus={}", request.getCaseId(), response.getPlanStatus());
        try {
            CorrespondenceRequestDTO corrRequest = new CorrespondenceRequestDTO();
            corrRequest.setCaseId(request.getCaseId());
            corrRequest.setPlanName(request.getPlanName());
            corrRequest.setPlanStatus(response.getPlanStatus());
            corrRequest.setDenialReason(response.getDenialReason());
            corrRequest.setCitizenName("Citizen-" + request.getCaseId());

            correspondenceFeignClient.generateNotice(corrRequest);
            log.info("Correspondence notice generated successfully: caseId={}", request.getCaseId());
        } catch (Exception e) {
            log.error("Correspondence service call failed: caseId={}, error={}", request.getCaseId(), e.getMessage(), e);
        }

        return response;
    }
}
