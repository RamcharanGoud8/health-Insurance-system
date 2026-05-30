package com.hi.datacollection.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EligibilityResponseDTO {

    private String planStatus;
    private Double benefitAmount;
    private String denialReason;
    private LocalDate startDate;
    private LocalDate endDate;
}