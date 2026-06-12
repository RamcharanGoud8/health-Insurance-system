package com.hi.applicationregistration.controller;

import com.hi.applicationregistration.dto.ApplicationRequest;
import com.hi.applicationregistration.entity.CitizenApplicationEntity;
import com.hi.applicationregistration.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    private final ApplicationService service;

    @PostMapping("/applications")
    public ResponseEntity<Long> createApplication(@RequestBody ApplicationRequest request) {
        log.info("POST /applications — create application request received: email={}, state={}",
                request.getEmail(), request.getStateName());

        Long appId = service.createApplication(request);

        log.info("POST /applications — application created successfully: appId={}", appId);
        return new ResponseEntity<>(appId, HttpStatus.CREATED);
    }

    @GetMapping("/get/{appId}")
    public CitizenApplicationEntity getApplicationById(@PathVariable Long appId) {
        log.info("GET /get/{} — fetch application by ID", appId);

        CitizenApplicationEntity result = service.getApplicationById(appId);

        if (result == null) {
            log.warn("GET /get/{} — application not found", appId);
        } else {
            log.info("GET /get/{} — application found: status={}", appId, result.getApplicationStatus());
        }
        return result;
    }

    @GetMapping("/getAll")
    public List<CitizenApplicationEntity> getAllApplications() {
        log.info("GET /getAll — fetch all applications");

        List<CitizenApplicationEntity> applications = service.getAllApplications();

        log.info("GET /getAll — returning {} applications", applications.size());
        return applications;
    }

    @PutMapping("/update/{appId}")
    public CitizenApplicationEntity updateApplication(
            @PathVariable Long appId,
            @RequestBody CitizenApplicationEntity application) {
        log.info("PUT /update/{} — update application request received: newStatus={}", appId, application.getApplicationStatus());

        CitizenApplicationEntity updated = service.updateApplication(appId, application);

        if (updated == null) {
            log.warn("PUT /update/{} — update failed, application not found", appId);
        } else {
            log.info("PUT /update/{} — application updated successfully", appId);
        }
        return updated;
    }

    @DeleteMapping("/delete/{appId}")
    public String deleteApplication(@PathVariable Long appId) {
        log.info("DELETE /delete/{} — delete application request received", appId);

        service.deleteApplication(appId);

        log.info("DELETE /delete/{} — application deleted successfully", appId);
        return "Application Deleted Successfully";
    }
}
