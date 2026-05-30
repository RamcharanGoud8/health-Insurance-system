package com.hi.correspondance.dto;


import lombok.Data;

@Data
public class CorrespondenceResponse {

    private Integer noticeId;

    private String message;

    private String pdfPath;
}