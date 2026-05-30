package com.hi.eligibilitydetermination.dto;

import lombok.Data;

@Data
public class BenefitRequestDTO {

    private Integer caseId;
    private String citizenName;
    private String planName;
    private Double benefitAmount;
}
