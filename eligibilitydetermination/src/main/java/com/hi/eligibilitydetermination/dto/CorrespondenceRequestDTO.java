package com.hi.eligibilitydetermination.dto;

import lombok.Data;

@Data
public class CorrespondenceRequestDTO {

    private Integer caseId;
    private String citizenName;
    private String planName;
    private String planStatus;
    private String denialReason;
}