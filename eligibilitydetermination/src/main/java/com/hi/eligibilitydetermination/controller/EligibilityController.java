package com.hi.eligibilitydetermination.controller;

import com.hi.eligibilitydetermination.dto.EligibilityRequest;
import com.hi.eligibilitydetermination.dto.EligibilityResponse;
import com.hi.eligibilitydetermination.service.EligibilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityController {

    private static final Logger log = LoggerFactory.getLogger(EligibilityController.class);

    private final EligibilityService service;

    public EligibilityController(EligibilityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EligibilityResponse> determineEligibility(
            @RequestBody EligibilityRequest request) {
        log.info("POST /api/eligibility — eligibility request received: caseId={}, planName={}, income={}, employmentStatus={}",
                request.getCaseId(), request.getPlanName(),
                request.getIncome(), request.getEmploymentStatus());

        EligibilityResponse response = service.determineEligibility(request);

        log.info("POST /api/eligibility — eligibility determined: caseId={}, planStatus={}, benefitAmount={}",
                request.getCaseId(), response.getPlanStatus(), response.getBenefitAmount());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
