package com.hi.applicationregistration.service;

import com.hi.applicationregistration.dto.ApplicationRequest;
import com.hi.applicationregistration.entity.CitizenApplicationEntity;

import java.util.List;

public interface ApplicationService {

    public Long createApplication(ApplicationRequest request);

    List<CitizenApplicationEntity> getAllApplications();

    // Get Application By Id
    CitizenApplicationEntity getApplicationById(Long appId);


    // Update Application
    CitizenApplicationEntity updateApplication(
            Long appId,
            CitizenApplicationEntity application);

    // Delete Application
    void deleteApplication(Long appId);
}
