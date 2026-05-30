package com.hi.correspondance.dto;

import lombok.Data;

@Data
public class CorrespondenceRequest {

    private Integer caseId;

    private String citizenName;

    private String planName;

    private String planStatus;

    private String denialReason;
}
