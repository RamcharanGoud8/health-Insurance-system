package com.hi.applicationregistration.controller;

import com.hi.applicationregistration.dto.ApplicationRequest;
import com.hi.applicationregistration.entity.CitizenApplicationEntity;
import com.hi.applicationregistration.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService service;

    @PostMapping("/applications")
    public ResponseEntity<Long> createApplication(@RequestBody ApplicationRequest request){

        Long appId = service.createApplication(request);
        return new ResponseEntity<>(
                appId,
                HttpStatus.CREATED);
    }

    @GetMapping("/get/{appId}")
    public CitizenApplicationEntity getApplicationById(@PathVariable Long appId) {

        return service.getApplicationById(appId);
    }


    @GetMapping("/getAll")
    public List<CitizenApplicationEntity> getAllApplications() {

        return service.getAllApplications();
    }
    // UPDATE
    @PutMapping("/update/{appId}")
    public CitizenApplicationEntity updateApplication(
            @PathVariable Long appId,
            @RequestBody CitizenApplicationEntity application) {

        return service.updateApplication(appId, application);
    }

    // DELETE
    @DeleteMapping("/delete/{appId}")
    public String deleteApplication(
            @PathVariable Long appId) {

        service.deleteApplication(appId);

        return "Application Deleted Successfully";
    }
}
