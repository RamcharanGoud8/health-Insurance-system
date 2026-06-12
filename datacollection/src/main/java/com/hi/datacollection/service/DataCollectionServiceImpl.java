package com.hi.datacollection.service;

import com.hi.datacollection.dto.DataCollectionRequest;
import com.hi.datacollection.dto.DataCollectionResponse;
import com.hi.datacollection.dto.EligibilityRequestDTO;
import com.hi.datacollection.entity.DataCollectionEntity;
import com.hi.datacollection.exception.ResourceNotFoundException;
import com.hi.datacollection.feign.EligibilityFeignClient;
import com.hi.datacollection.repository.DataCollectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataCollectionServiceImpl implements DataCollectionService {

    private static final Logger log = LoggerFactory.getLogger(DataCollectionServiceImpl.class);

    private final DataCollectionRepository repository;

    @Autowired
    private EligibilityFeignClient eligibilityFeignClient;

    public DataCollectionServiceImpl(DataCollectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataCollectionResponse createCase(DataCollectionRequest request) {
        log.info("Creating new case: planName={}, employmentStatus={}, income={}",
                request.getPlanName(), request.getEmploymentStatus(), request.getIncome());

        DataCollectionEntity entity = new DataCollectionEntity();
        BeanUtils.copyProperties(request, entity);
        DataCollectionEntity savedEntity = repository.save(entity);
        log.info("Case saved to DB: caseId={}", savedEntity.getCaseId());

        log.info("Calling eligibility service: caseId={}, planName={}", savedEntity.getCaseId(), request.getPlanName());
        try {
            EligibilityRequestDTO eligibilityRequest = new EligibilityRequestDTO();
            eligibilityRequest.setCaseId(savedEntity.getCaseId());
            eligibilityRequest.setPlanName(request.getPlanName());
            eligibilityRequest.setIncome(Double.parseDouble(request.getIncome()));
            eligibilityRequest.setEmploymentStatus(request.getEmploymentStatus());
            eligibilityRequest.setEducation(request.getEducation());
            eligibilityRequest.setPropertyDetails(request.getPropertyDetails());

            eligibilityFeignClient.determineEligibility(eligibilityRequest);
            log.info("Eligibility service called successfully: caseId={}", savedEntity.getCaseId());
        } catch (Exception e) {
            log.error("Eligibility service call failed: caseId={}, error={}", savedEntity.getCaseId(), e.getMessage(), e);
        }

        return new DataCollectionResponse(savedEntity.getCaseId(), "Case Created Successfully");
    }

    @Override
    public DataCollectionRequest getCaseById(Long caseId) {
        log.debug("Fetching case by ID: caseId={}", caseId);
        DataCollectionEntity entity = repository.findById(caseId)
                .orElseThrow(() -> {
                    log.warn("Case not found: caseId={}", caseId);
                    return new ResourceNotFoundException("Case Not Found");
                });

        DataCollectionRequest response = new DataCollectionRequest();
        BeanUtils.copyProperties(entity, response);
        log.info("Case found: caseId={}, planName={}", caseId, entity.getPlanName());
        return response;
    }

    @Override
    public List<DataCollectionEntity> getAllCases() {
        log.debug("Fetching all cases");
        List<DataCollectionEntity> cases = repository.findAll();
        log.info("Retrieved all cases: count={}", cases.size());
        return cases;
    }

    @Override
    public String updateCase(Long caseId, DataCollectionRequest request) {
        log.info("Updating case: caseId={}", caseId);
        DataCollectionEntity entity = repository.findById(caseId)
                .orElseThrow(() -> {
                    log.warn("Update failed — case not found: caseId={}", caseId);
                    return new ResourceNotFoundException("Case Not Found");
                });

        BeanUtils.copyProperties(request, entity);
        repository.save(entity);
        log.info("Case updated successfully: caseId={}", caseId);
        return "Case Updated Successfully";
    }

    @Override
    public String deleteCase(Long caseId) {
        log.info("Deleting case: caseId={}", caseId);
        DataCollectionEntity entity = repository.findById(caseId)
                .orElseThrow(() -> {
                    log.warn("Delete failed — case not found: caseId={}", caseId);
                    return new ResourceNotFoundException("Case Not Found");
                });

        repository.delete(entity);
        log.info("Case deleted successfully: caseId={}", caseId);
        return "Case Deleted Successfully";
    }
}
