package com.hi.eligibilitydetermination.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ELIGIBILITY_DETAILS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer edTraceId;

    private Integer caseId;

    private String planName;

    private String planStatus;

    private Double benefitAmount;

    private String denialReason;

    private LocalDate planStartDate;

    private LocalDate planEndDate;

    private LocalDate createdDate;
}
