package com.hi.benifitIssuance.dto;

import lombok.Data;

@Data
public class BenefitRequest {

    private Integer caseId;

    private String citizenName;

    private String planName;

    private Double benefitAmount;
}