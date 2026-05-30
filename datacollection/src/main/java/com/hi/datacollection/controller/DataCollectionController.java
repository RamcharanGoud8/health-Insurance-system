package com.hi.datacollection.controller;

import com.hi.datacollection.dto.DataCollectionRequest;
import com.hi.datacollection.dto.DataCollectionResponse;
import com.hi.datacollection.entity.DataCollectionEntity;
import com.hi.datacollection.service.DataCollectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data-collection")
public class DataCollectionController {

    private final DataCollectionService service;

    public DataCollectionController(DataCollectionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DataCollectionResponse> createCase(
            @Valid @RequestBody DataCollectionRequest request) {

        return new ResponseEntity<>(service.createCase(request), HttpStatus.CREATED);
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<DataCollectionRequest> getCaseById(@PathVariable Long caseId) {

        return new ResponseEntity<>(service.getCaseById(caseId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DataCollectionEntity>> getAllCases() {

        return new ResponseEntity<>(service.getAllCases(), HttpStatus.OK);
    }

    @PutMapping("/{caseId}")
    public ResponseEntity<String> updateCase(
            @PathVariable Long caseId,
            @RequestBody DataCollectionRequest request) {

        return new ResponseEntity<>(service.updateCase(caseId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{caseId}")
    public ResponseEntity<String> deleteCase(@PathVariable Long caseId) {

        return new ResponseEntity<>(service.deleteCase(caseId), HttpStatus.OK);
    }
}
