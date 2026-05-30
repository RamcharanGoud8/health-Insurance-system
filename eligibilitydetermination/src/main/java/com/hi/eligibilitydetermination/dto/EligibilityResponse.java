package com.hi.eligibilitydetermination.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityResponse {

        private String planStatus;

        private Double benefitAmount;

        private String denialReason;

        private LocalDate startDate;

        private LocalDate endDate;
}
