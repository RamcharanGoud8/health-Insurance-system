package com.hi.applicationregistration.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="citizen_application")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitizenApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false)
    private String gender;

    @Column(unique = true, nullable = false)
    private Long ssn;

    @Column(nullable = false)
    private Long mobileNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String stateName;

    private String applicationStatus;
}
