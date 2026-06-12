package com.hi.benifitIssuance.service;

import com.hi.benifitIssuance.dto.BenefitRequest;
import com.hi.benifitIssuance.dto.BenefitResponse;
import com.hi.benifitIssuance.entity.Benefit;
import com.hi.benifitIssuance.repository.BenefitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BenefitServiceImpl implements BenefitService {

    private static final Logger log = LoggerFactory.getLogger(BenefitServiceImpl.class);

    @Autowired
    private BenefitRepository repository;

    @Override
    public BenefitResponse issueBenefit(BenefitRequest request) {
        log.info("Issuing benefit: caseId={}, planName={}, benefitAmount={}",
                request.getCaseId(), request.getPlanName(), request.getBenefitAmount());

        Benefit benefit = new Benefit();
        benefit.setCaseId(request.getCaseId());
        benefit.setCitizenName(request.getCitizenName());
        benefit.setPlanName(request.getPlanName());
        benefit.setBenefitAmount(request.getBenefitAmount());
        benefit.setStartDate(LocalDate.now());
        benefit.setEndDate(LocalDate.now().plusMonths(6));
        benefit.setPaymentStatus("PENDING");
        benefit.setBenefitStatus("ACTIVE");

        Benefit saved = repository.save(benefit);
        log.info("Benefit issued successfully: benefitId={}, caseId={}, planName={}, status={}, startDate={}, endDate={}",
                saved.getBenefitId(), saved.getCaseId(), saved.getPlanName(),
                saved.getBenefitStatus(), saved.getStartDate(), saved.getEndDate());

        BenefitResponse response = new BenefitResponse();
        response.setBenefitId(saved.getBenefitId());
        response.setMessage("Benefit Issued Successfully");
        response.setPaymentStatus(saved.getPaymentStatus());

        return response;
    }

    @Override
    public Benefit getBenefitByCaseId(Integer caseId) {
        log.debug("Fetching benefit by caseId: caseId={}", caseId);
        Benefit benefit = repository.findByCaseId(caseId);

        if (benefit == null) {
            log.warn("No benefit found for caseId={}", caseId);
        } else {
            log.info("Benefit found: caseId={}, benefitId={}, status={}", caseId, benefit.getBenefitId(), benefit.getBenefitStatus());
        }

        return benefit;
    }

    @Override
    public List<Benefit> getAllBenefits() {
        log.debug("Fetching all benefits");
        List<Benefit> benefits = repository.findAll();
        log.info("Retrieved all benefits: count={}", benefits.size());
        return benefits;
    }

    @Override
    public String updatePaymentStatus(Integer benefitId, String status) {
        log.info("Updating payment status: benefitId={}, newStatus={}", benefitId, status);
        Benefit benefit = repository.findById(benefitId).orElse(null);

        if (benefit == null) {
            log.warn("Payment status update failed — benefit not found: benefitId={}", benefitId);
            return "Benefit Not Found";
        }

        String previousStatus = benefit.getPaymentStatus();
        benefit.setPaymentStatus(status);
        repository.save(benefit);

        log.info("Payment status updated: benefitId={}, previousStatus={}, newStatus={}",
                benefitId, previousStatus, status);
        return "Payment Status Updated";
    }

    @Override
    public String terminateBenefit(Integer benefitId) {
        log.info("Terminating benefit: benefitId={}", benefitId);
        Benefit benefit = repository.findById(benefitId).orElse(null);

        if (benefit == null) {
            log.warn("Termination failed — benefit not found: benefitId={}", benefitId);
            return "Benefit Not Found";
        }

        benefit.setBenefitStatus("INACTIVE");
        repository.save(benefit);

        log.info("Benefit terminated: benefitId={}, caseId={}, planName={}", benefitId, benefit.getCaseId(), benefit.getPlanName());
        return "Benefit Terminated";
    }
}
