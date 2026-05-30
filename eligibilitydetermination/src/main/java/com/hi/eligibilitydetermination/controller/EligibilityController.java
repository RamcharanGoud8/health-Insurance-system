package com.hi.eligibilitydetermination.controller;

import com.hi.eligibilitydetermination.dto.EligibilityRequest;
import com.hi.eligibilitydetermination.dto.EligibilityResponse;
import com.hi.eligibilitydetermination.service.EligibilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityController {

    private final EligibilityService service;

    public EligibilityController(
            EligibilityService service) {

        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EligibilityResponse> determineEligibility(
            @RequestBody EligibilityRequest request) {

        EligibilityResponse response =
                service.determineEligibility(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }
}