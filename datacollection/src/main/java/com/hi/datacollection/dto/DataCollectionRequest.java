package com.hi.datacollection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DataCollectionRequest {

    @NotNull(message = "App Id is required")
    private Integer appId;


    private String planName;

    private String income;

    private String education;

    private String employmentStatus;

    private String propertyDetails;

    private String bankName;

    private String accountNumber;

    private String ifscCode;
}
