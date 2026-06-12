package com.hi.datacollection.controller;

import com.hi.datacollection.dto.DataCollectionRequest;
import com.hi.datacollection.dto.DataCollectionResponse;
import com.hi.datacollection.entity.DataCollectionEntity;
import com.hi.datacollection.service.DataCollectionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data-collection")
public class DataCollectionController {

    private static final Logger log = LoggerFactory.getLogger(DataCollectionController.class);

    private final DataCollectionService service;

    public DataCollectionController(DataCollectionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DataCollectionResponse> createCase(
            @Valid @RequestBody DataCollectionRequest request) {
        log.info("POST /api/data-collection — create case request received: planName={}, employmentStatus={}, income={}",
                request.getPlanName(), request.getEmploymentStatus(), request.getIncome());

        DataCollectionResponse response = service.createCase(request);

        log.info("POST /api/data-collection — case created: caseId={}", response.getCaseId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<DataCollectionRequest> getCaseById(@PathVariable Long caseId) {
        log.info("GET /api/data-collection/{} — fetch case by ID", caseId);

        DataCollectionRequest result = service.getCaseById(caseId);

        log.info("GET /api/data-collection/{} — case found: planName={}", caseId, result.getPlanName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DataCollectionEntity>> getAllCases() {
        log.info("GET /api/data-collection — fetch all cases");

        List<DataCollectionEntity> cases = service.getAllCases();

        log.info("GET /api/data-collection — returning {} cases", cases.size());
        return new ResponseEntity<>(cases, HttpStatus.OK);
    }

    @PutMapping("/{caseId}")
    public ResponseEntity<String> updateCase(
            @PathVariable Long caseId,
            @RequestBody DataCollectionRequest request) {
        log.info("PUT /api/data-collection/{} — update case request received", caseId);

        String result = service.updateCase(caseId, request);

        log.info("PUT /api/data-collection/{} — result: {}", caseId, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{caseId}")
    public ResponseEntity<String> deleteCase(@PathVariable Long caseId) {
        log.info("DELETE /api/data-collection/{} — delete case request received", caseId);

        String result = service.deleteCase(caseId);

        log.info("DELETE /api/data-collection/{} — result: {}", caseId, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
