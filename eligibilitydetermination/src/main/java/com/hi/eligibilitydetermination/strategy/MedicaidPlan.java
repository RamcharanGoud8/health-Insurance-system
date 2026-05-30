package com.hi.eligibilitydetermination.strategy;

import com.hi.eligibilitydetermination.dto.EligibilityRequest;
import com.hi.eligibilitydetermination.dto.EligibilityResponse;
import com.hi.eligibilitydetermination.repository.EligibilityRepository;
import org.springframework.stereotype.Service;

@Service("MEDICAID")
public class MedicaidPlan implements PlanStrategy {

    @Override
    public EligibilityResponse determineEligibility(
            EligibilityRequest request) {

        EligibilityResponse response = new EligibilityResponse();

        if (request.getIncome() <= 25000) {

            response.setPlanStatus("APPROVED");
            response.setBenefitAmount(500.0);

        } else {

            response.setPlanStatus("DENIED");
            response.setDenialReason("Income exceeds Medicaid eligibility limit (25000)");
        }

        return response;
    }
}
