package com.hi.benifitIssuance.service;


import com.hi.benifitIssuance.dto.BenefitRequest;
import com.hi.benifitIssuance.dto.BenefitResponse;
import com.hi.benifitIssuance.entity.Benefit;
import com.hi.benifitIssuance.repository.BenefitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BenefitServiceImpl implements BenefitService {

    @Autowired
    private BenefitRepository repository;

    @Override
    public BenefitResponse issueBenefit(
            BenefitRequest request) {

        Benefit benefit = new Benefit();

        benefit.setCaseId(
                request.getCaseId());

        benefit.setCitizenName(
                request.getCitizenName());

        benefit.setPlanName(
                request.getPlanName());

        benefit.setBenefitAmount(
                request.getBenefitAmount());

        benefit.setStartDate(
                LocalDate.now());

        benefit.setEndDate(
                LocalDate.now().plusMonths(6));

        benefit.setPaymentStatus(
                "PENDING");

        benefit.setBenefitStatus(
                "ACTIVE");

        Benefit saved =
                repository.save(benefit);

        BenefitResponse response =
                new BenefitResponse();

        response.setBenefitId(
                saved.getBenefitId());

        response.setMessage(
                "Benefit Issued Successfully");

        response.setPaymentStatus(
                saved.getPaymentStatus());

        return response;
    }

    @Override
    public Benefit getBenefitByCaseId(
            Integer caseId) {

        return repository.findByCaseId(caseId);
    }

    @Override
    public List<Benefit> getAllBenefits() {

        return repository.findAll();
    }

    @Override
    public String updatePaymentStatus(
            Integer benefitId,
            String status) {

        Benefit benefit =
                repository.findById(benefitId)
                        .orElse(null);

        if (benefit == null) {
            return "Benefit Not Found";
        }

        benefit.setPaymentStatus(status);

        repository.save(benefit);

        return "Payment Status Updated";
    }

    @Override
    public String terminateBenefit(
            Integer benefitId) {

        Benefit benefit =
                repository.findById(benefitId)
                        .orElse(null);

        if (benefit == null) {
            return "Benefit Not Found";
        }

        benefit.setBenefitStatus(
                "INACTIVE");

        repository.save(benefit);

        return "Benefit Terminated";
    }
}
