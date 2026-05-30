package com.hi.datacollection.dto;

import lombok.Data;

@Data
public class EligibilityRequestDTO {

    private Integer caseId;
    private String planName;
    private Double income;
    private String employmentStatus;
    private String education;
    private String propertyDetails;
}