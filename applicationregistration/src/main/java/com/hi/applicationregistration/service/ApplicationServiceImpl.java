package com.hi.applicationregistration.service;

import com.hi.applicationregistration.dto.ApplicationRequest;
import com.hi.applicationregistration.dto.ApplicationToDataCollectionRequest;

import com.hi.applicationregistration.entity.CitizenApplicationEntity;
import com.hi.applicationregistration.feign.DataCollectionFeignClient;
import com.hi.applicationregistration.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{

    @Autowired
    private DataCollectionFeignClient dataCollectionFeignClient;

    private  final ApplicationRepository repository;

    @Override
    public Long createApplication(ApplicationRequest request) {

        CitizenApplicationEntity entity =
                CitizenApplicationEntity.builder()
                        .fullName(request.getFullName())
                        .email(request.getEmail())
                        .mobileNumber(request.getMobileNumber())
                        .gender(request.getGender())
                        .dob(request.getDob())
                        .ssn(request.getSsn())
                        .stateName(request.getStateName())
                        .applicationStatus("CREATED")
                        .build();

        CitizenApplicationEntity saved =
                repository.save(entity);


        // Create Feign DTO
        ApplicationToDataCollectionRequest dataRequest =
                new ApplicationToDataCollectionRequest();

        dataRequest.setAppId(saved.getAppId().intValue());



        // Call Data Collection Service
        dataCollectionFeignClient
                .saveData(dataRequest);

        return saved.getAppId();
    }

    @Override
    public List<CitizenApplicationEntity> getAllApplications() {

        List<CitizenApplicationEntity> all = repository.findAll();

        return all;
    }

    @Override
    public CitizenApplicationEntity getApplicationById(Long appId) {

        Optional<CitizenApplicationEntity> byId = repository.findById(appId);

        return byId.orElse(null);
    }

    @Override
    public CitizenApplicationEntity updateApplication(Long appId, CitizenApplicationEntity application) {
        Optional<CitizenApplicationEntity> byId = repository.findById(appId);

        if(byId.isEmpty()){
            return null;
        }

        CitizenApplicationEntity existing = byId.get();

        existing.setFullName(application.getFullName());
        existing.setEmail(application.getEmail());
        existing.setMobileNumber(application.getMobileNumber());
        existing.setGender(application.getGender());
        existing.setDob(application.getDob());
        existing.setSsn(application.getSsn());
        existing.setStateName(application.getStateName());
        existing.setApplicationStatus(application.getApplicationStatus());

        CitizenApplicationEntity updated = repository.save(existing);

        return updated;
    }

    @Override
    public void deleteApplication(Long appId) {

        Optional<CitizenApplicationEntity> byId = repository.findById(appId);

        if(byId.isEmpty()){
            return;
        }

         repository.deleteById(appId);
    }
}
