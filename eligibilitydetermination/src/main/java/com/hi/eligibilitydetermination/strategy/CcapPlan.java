package com.hi.eligibilitydetermination.strategy;

import com.hi.eligibilitydetermination.dto.EligibilityRequest;
import com.hi.eligibilitydetermination.dto.EligibilityResponse;
import org.springframework.stereotype.Service;

@Service("CCAP")
public class CcapPlan implements PlanStrategy{


    @Override
    public EligibilityResponse determineEligibility(
            EligibilityRequest request) {

        EligibilityResponse response =
                new EligibilityResponse();

        if (request.getIncome() <= 40000 &&
                "EMPLOYED".equalsIgnoreCase(request.getEmploymentStatus())) {

            response.setPlanStatus("APPROVED");
            response.setBenefitAmount(700.0);

        } else {

            response.setPlanStatus("DENIED");

            if (request.getIncome() > 40000) {
                response.setDenialReason("Income exceeds CCAP limit (40000)");
            } else {
                response.setDenialReason("Employment status must be EMPLOYED for CCAP");
            }
        }

        return response;
    }
}
