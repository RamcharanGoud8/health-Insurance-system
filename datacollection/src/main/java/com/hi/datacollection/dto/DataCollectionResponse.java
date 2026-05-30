package com.hi.datacollection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataCollectionResponse {

    private Integer caseId;

    private String message;
}
