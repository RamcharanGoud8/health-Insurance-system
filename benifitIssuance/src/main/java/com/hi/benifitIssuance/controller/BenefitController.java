package com.hi.benifitIssuance.controller;


import com.hi.benifitIssuance.dto.BenefitRequest;
import com.hi.benifitIssuance.dto.BenefitResponse;
import com.hi.benifitIssuance.entity.Benefit;
import com.hi.benifitIssuance.service.BenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/benefits")
public class BenefitController {

    @Autowired
    private BenefitService service;

    @PostMapping("/issue")
    public BenefitResponse issueBenefit(
            @RequestBody BenefitRequest request) {

        return service.issueBenefit(request);
    }

    @GetMapping("/{caseId}")
    public Benefit getBenefitByCaseId(
            @PathVariable Integer caseId) {

        return service.getBenefitByCaseId(caseId);
    }

    @GetMapping
    public List<Benefit> getAllBenefits() {

        return service.getAllBenefits();
    }

    @PutMapping("/payment-status/{benefitId}")
    public String updatePaymentStatus(
            @PathVariable Integer benefitId,
            @RequestParam String status) {

        return service.updatePaymentStatus(
                benefitId,
                status
        );
    }

    @PutMapping("/terminate/{benefitId}")
    public String terminateBenefit(
            @PathVariable Integer benefitId) {

        return service.terminateBenefit(
                benefitId
        );
    }
}
