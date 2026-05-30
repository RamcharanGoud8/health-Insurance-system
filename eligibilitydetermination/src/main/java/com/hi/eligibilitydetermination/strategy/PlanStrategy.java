package com.hi.eligibilitydetermination.strategy;

import com.hi.eligibilitydetermination.dto.EligibilityRequest;
import com.hi.eligibilitydetermination.dto.EligibilityResponse;

public interface PlanStrategy {
    EligibilityResponse determineEligibility(
            EligibilityRequest request
    );
}
