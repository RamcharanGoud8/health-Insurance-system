package com.hi.benifitIssuance.dto;


import lombok.Data;

@Data
public class BenefitResponse {

    private Integer benefitId;

    private String message;

    private String paymentStatus;
}