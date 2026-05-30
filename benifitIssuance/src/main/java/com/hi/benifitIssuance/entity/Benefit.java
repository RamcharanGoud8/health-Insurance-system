package com.hi.benifitIssuance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "benefits")
@Data
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer benefitId;

    private Integer caseId;

    private String citizenName;

    private String planName;

    private Double benefitAmount;

    private LocalDate startDate;

    private LocalDate endDate;

    private String paymentStatus;

    private String benefitStatus;
}
