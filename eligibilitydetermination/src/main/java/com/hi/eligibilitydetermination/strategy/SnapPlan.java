package com.hi.eligibilitydetermination.strategy;

import com.hi.eligibilitydetermination.dto.EligibilityRequest;
import com.hi.eligibilitydetermination.dto.EligibilityResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("SNAP")
public class SnapPlan implements PlanStrategy {

    @Override
    public EligibilityResponse determineEligibility(
            EligibilityRequest request) {

        EligibilityResponse response = new EligibilityResponse();

        if(request.getIncome() <= 30000) {

            response.setPlanStatus("APPROVED");
            response.setBenefitAmount(350.00);
            response.setStartDate(LocalDate.now());
            response.setEndDate(LocalDate.now().plusMonths(6));

        } else {

            response.setPlanStatus("DENIED");
            response.setDenialReason("Income exceeds SNAP limit");
        }

        return response;
    }
}
