package com.hi.eligibilitydetermination.service;

import com.hi.eligibilitydetermination.dto.EligibilityRequest;
import com.hi.eligibilitydetermination.dto.EligibilityResponse;

public interface EligibilityService {

    EligibilityResponse determineEligibility(
            EligibilityRequest request
    );
}
