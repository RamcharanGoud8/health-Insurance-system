package com.hi.applicationregistration.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationRequest {

    private String fullName;

    private String email;

    private Long mobileNumber;

    private String gender;

    private LocalDate dob;

    private Long ssn;

    private String applicationStatus;
    private String stateName;
}
