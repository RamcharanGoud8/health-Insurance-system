package com.hi.applicationregistration.service;

import com.hi.applicationregistration.dto.ApplicationRequest;
import com.hi.applicationregistration.dto.ApplicationToDataCollectionRequest;
import com.hi.applicationregistration.entity.CitizenApplicationEntity;
import com.hi.applicationregistration.feign.DataCollectionFeignClient;
import com.hi.applicationregistration.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Autowired
    private DataCollectionFeignClient dataCollectionFeignClient;

    private final ApplicationRepository repository;

    @Override
    public Long createApplication(ApplicationRequest request) {
        log.info("Received request to create application: email={}, state={}, gender={}",
                request.getEmail(), request.getStateName(), request.getGender());

        CitizenApplicationEntity entity = CitizenApplicationEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .gender(request.getGender())
                .dob(request.getDob())
                .ssn(request.getSsn())
                .stateName(request.getStateName())
                .applicationStatus("CREATED")
                .build();

        CitizenApplicationEntity saved = repository.save(entity);
        log.info("Application persisted successfully: appId={}, status={}", saved.getAppId(), saved.getApplicationStatus());

        ApplicationToDataCollectionRequest dataRequest = new ApplicationToDataCollectionRequest();
        dataRequest.setAppId(saved.getAppId().intValue());

        log.info("Forwarding application to data-collection service: appId={}", saved.getAppId());
        try {
            dataCollectionFeignClient.saveData(dataRequest);
            log.info("Data-collection service notified successfully: appId={}", saved.getAppId());
        } catch (Exception e) {
            log.error("Failed to notify data-collection service: appId={}, error={}", saved.getAppId(), e.getMessage(), e);
        }

        return saved.getAppId();
    }

    @Override
    public List<CitizenApplicationEntity> getAllApplications() {
        log.debug("Fetching all applications");
        List<CitizenApplicationEntity> all = repository.findAll();
        log.info("Retrieved all applications: count={}", all.size());
        return all;
    }

    @Override
    public CitizenApplicationEntity getApplicationById(Long appId) {
        log.debug("Fetching application by ID: appId={}", appId);
        Optional<CitizenApplicationEntity> byId = repository.findById(appId);

        if (byId.isEmpty()) {
            log.warn("Application not found: appId={}", appId);
            return null;
        }

        log.info("Application found: appId={}, status={}", appId, byId.get().getApplicationStatus());
        return byId.get();
    }

    @Override
    public CitizenApplicationEntity updateApplication(Long appId, CitizenApplicationEntity application) {
        log.info("Received request to update application: appId={}", appId);
        Optional<CitizenApplicationEntity> byId = repository.findById(appId);

        if (byId.isEmpty()) {
            log.warn("Update failed — application not found: appId={}", appId);
            return null;
        }

        CitizenApplicationEntity existing = byId.get();
        String previousStatus = existing.getApplicationStatus();

        existing.setFullName(application.getFullName());
        existing.setEmail(application.getEmail());
        existing.setMobileNumber(application.getMobileNumber());
        existing.setGender(application.getGender());
        existing.setDob(application.getDob());
        existing.setSsn(application.getSsn());
        existing.setStateName(application.getStateName());
        existing.setApplicationStatus(application.getApplicationStatus());

        CitizenApplicationEntity updated = repository.save(existing);
        log.info("Application updated: appId={}, previousStatus={}, newStatus={}",
                appId, previousStatus, updated.getApplicationStatus());

        return updated;
    }

    @Override
    public void deleteApplication(Long appId) {
        log.info("Received request to delete application: appId={}", appId);
        Optional<CitizenApplicationEntity> byId = repository.findById(appId);

        if (byId.isEmpty()) {
            log.warn("Delete skipped — application not found: appId={}", appId);
            return;
        }

        repository.deleteById(appId);
        log.info("Application deleted successfully: appId={}", appId);
    }
}
