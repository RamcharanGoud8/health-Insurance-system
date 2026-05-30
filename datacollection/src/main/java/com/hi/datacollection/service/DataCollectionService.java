package com.hi.datacollection.service;

import com.hi.datacollection.dto.DataCollectionRequest;
import com.hi.datacollection.dto.DataCollectionResponse;
import com.hi.datacollection.entity.DataCollectionEntity;

import java.util.List;

public interface DataCollectionService {

    DataCollectionResponse createCase(DataCollectionRequest request);

    DataCollectionRequest getCaseById(Long caseId);

    List<DataCollectionEntity> getAllCases();

    String updateCase(Long caseId, DataCollectionRequest request);

    String deleteCase(Long caseId);
}
