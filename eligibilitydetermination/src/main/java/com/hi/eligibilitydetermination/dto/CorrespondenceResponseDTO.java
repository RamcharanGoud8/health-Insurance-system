package com.hi.eligibilitydetermination.dto;

import lombok.Data;

@Data
public class CorrespondenceResponseDTO {

    private Integer noticeId;
    private String message;
    private String pdfPath;
}
