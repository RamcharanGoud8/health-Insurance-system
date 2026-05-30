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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EligibilityServiceImpl implements EligibilityService {

    private final PlanFactory planFactory;
    private final EligibilityRepository repository;

    @Autowired
    private BenefitFeignClient benefitFeignClient;

    @Autowired
    private CorrespondenceFeignClient correspondenceFeignClient;

    public EligibilityServiceImpl(
            PlanFactory planFactory,
            EligibilityRepository repository) {

        this.planFactory = planFactory;
        this.repository = repository;
    }

    @Override
    public EligibilityResponse determineEligibility(
            EligibilityRequest request) {

        // Step 1 - Run eligibility logic
        PlanStrategy strategy =
                planFactory.getPlan(request.getPlanName());

        EligibilityResponse response =
                strategy.determineEligibility(request);

        // Step 2 - Save result to DB
        EligibilityDetailsEntity entity =
                new EligibilityDetailsEntity();

        BeanUtils.copyProperties(response, entity);

        entity.setCaseId(request.getCaseId());
        entity.setPlanName(request.getPlanName());
        entity.setCreatedDate(LocalDate.now());

        repository.save(entity);

        // Step 3 - If APPROVED call Benefit Issuance
        if ("APPROVED".equals(response.getPlanStatus())) {

            BenefitRequestDTO benefitRequest =
                    new BenefitRequestDTO();

            benefitRequest.setCaseId(request.getCaseId());
            benefitRequest.setPlanName(request.getPlanName());
            benefitRequest.setBenefitAmount(response.getBenefitAmount());
            benefitRequest.setCitizenName("Citizen-" + request.getCaseId());

            benefitFeignClient.issueBenefit(benefitRequest);
        }

        // Step 4 - Always generate correspondence notice
        CorrespondenceRequestDTO corrRequest =
                new CorrespondenceRequestDTO();

        corrRequest.setCaseId(request.getCaseId());
        corrRequest.setPlanName(request.getPlanName());
        corrRequest.setPlanStatus(response.getPlanStatus());
        corrRequest.setDenialReason(response.getDenialReason());
        corrRequest.setCitizenName("Citizen-" + request.getCaseId());

        correspondenceFeignClient.generateNotice(corrRequest);

        return response;
    }
}