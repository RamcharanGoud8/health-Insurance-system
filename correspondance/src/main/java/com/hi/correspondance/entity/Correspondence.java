package com.hi.correspondance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "correspondence")
@Data
public  class Correspondence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noticeId;

    private Integer caseId;

    private String citizenName;

    private String planName;

    private String planStatus;

    private String denialReason;

    private LocalDate generatedDate;

    private String noticeStatus;

    private String pdfPath;
}