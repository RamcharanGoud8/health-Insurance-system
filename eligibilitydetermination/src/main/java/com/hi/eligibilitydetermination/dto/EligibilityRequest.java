package com.hi.eligibilitydetermination.dto;

import lombok.Data;

@Data
public class EligibilityRequest {
    private Integer caseId;

    private String planName;

    private Double income;

    private String employmentStatus;

    private String education;

    private String propertyDetails;
}
