package com.hi.benifitIssuance.controller;

import com.hi.benifitIssuance.dto.BenefitRequest;
import com.hi.benifitIssuance.dto.BenefitResponse;
import com.hi.benifitIssuance.entity.Benefit;
import com.hi.benifitIssuance.service.BenefitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/benefits")
public class BenefitController {

    private static final Logger log = LoggerFactory.getLogger(BenefitController.class);

    @Autowired
    private BenefitService service;

    @PostMapping("/issue")
    public BenefitResponse issueBenefit(@RequestBody BenefitRequest request) {
        log.info("POST /benefits/issue — issue benefit request received: caseId={}, planName={}, amount={}",
                request.getCaseId(), request.getPlanName(), request.getBenefitAmount());

        BenefitResponse response = service.issueBenefit(request);

        log.info("POST /benefits/issue — benefit issued: benefitId={}, paymentStatus={}",
                response.getBenefitId(), response.getPaymentStatus());
        return response;
    }

    @GetMapping("/{caseId}")
    public Benefit getBenefitByCaseId(@PathVariable Integer caseId) {
        log.info("GET /benefits/{} — fetch benefit by caseId", caseId);

        Benefit benefit = service.getBenefitByCaseId(caseId);

        if (benefit == null) {
            log.warn("GET /benefits/{} — no benefit found", caseId);
        } else {
            log.info("GET /benefits/{} — benefit found: benefitId={}, status={}",
                    caseId, benefit.getBenefitId(), benefit.getBenefitStatus());
        }
        return benefit;
    }

    @GetMapping
    public List<Benefit> getAllBenefits() {
        log.info("GET /benefits — fetch all benefits");

        List<Benefit> benefits = service.getAllBenefits();

        log.info("GET /benefits — returning {} benefits", benefits.size());
        return benefits;
    }

    @PutMapping("/payment-status/{benefitId}")
    public String updatePaymentStatus(
            @PathVariable Integer benefitId,
            @RequestParam String status) {
        log.info("PUT /benefits/payment-status/{} — update payment status request received: newStatus={}",
                benefitId, status);

        String result = service.updatePaymentStatus(benefitId, status);

        log.info("PUT /benefits/payment-status/{} — result: {}", benefitId, result);
        return result;
    }

    @PutMapping("/terminate/{benefitId}")
    public String terminateBenefit(@PathVariable Integer benefitId) {
        log.info("PUT /benefits/terminate/{} — terminate benefit request received", benefitId);

        String result = service.terminateBenefit(benefitId);

        log.info("PUT /benefits/terminate/{} — result: {}", benefitId, result);
        return result;
    }
}
