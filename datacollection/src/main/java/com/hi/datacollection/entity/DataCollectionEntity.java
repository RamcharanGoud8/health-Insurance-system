package com.hi.datacollection.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="DC_CASES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataCollectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer caseId;

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