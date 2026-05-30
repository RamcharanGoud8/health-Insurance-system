package com.hi.datacollection.service;

import com.hi.datacollection.dto.DataCollectionRequest;
import com.hi.datacollection.dto.DataCollectionResponse;
import com.hi.datacollection.dto.EligibilityRequestDTO;
import com.hi.datacollection.entity.DataCollectionEntity;
import com.hi.datacollection.exception.ResourceNotFoundException;
import com.hi.datacollection.feign.EligibilityFeignClient;
import com.hi.datacollection.repository.DataCollectionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataCollectionServiceImpl implements DataCollectionService {

    private final DataCollectionRepository repository;

    @Autowired
    private EligibilityFeignClient eligibilityFeignClient;

    public DataCollectionServiceImpl(DataCollectionRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataCollectionResponse createCase(DataCollectionRequest request) {

        // Step 1 - Save case to DB
        DataCollectionEntity entity = new DataCollectionEntity();
        BeanUtils.copyProperties(request, entity);
        DataCollectionEntity savedEntity = repository.save(entity);

        // Step 2 - Call Eligibility Service
        try {
            EligibilityRequestDTO eligibilityRequest = new EligibilityRequestDTO();
            eligibilityRequest.setCaseId(savedEntity.getCaseId());
            eligibilityRequest.setPlanName(request.getPlanName());
            eligibilityRequest.setIncome(Double.parseDouble(request.getIncome()));
            eligibilityRequest.setEmploymentStatus(request.getEmploymentStatus());
            eligibilityRequest.setEducation(request.getEducation());
            eligibilityRequest.setPropertyDetails(request.getPropertyDetails());

            eligibilityFeignClient.determineEligibility(eligibilityRequest);

        } catch (Exception e) {
            System.out.println("Eligibility call failed: " + e.getMessage());
            e.printStackTrace();
        }

        return new DataCollectionResponse(savedEntity.getCaseId(), "Case Created Successfully");
    }

    @Override
    public DataCollectionRequest getCaseById(Long caseId) {

        DataCollectionEntity entity = repository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Case Not Found"));

        DataCollectionRequest response = new DataCollectionRequest();
        BeanUtils.copyProperties(entity, response);

        return response;
    }

    @Override
    public List<DataCollectionEntity> getAllCases() {
        return repository.findAll();
    }

    @Override
    public String updateCase(Long caseId, DataCollectionRequest request) {

        DataCollectionEntity entity = repository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Case Not Found"));

        BeanUtils.copyProperties(request, entity);
        repository.save(entity);

        return "Case Updated Successfully";
    }

    @Override
    public String deleteCase(Long caseId) {

        DataCollectionEntity entity = repository.findById(caseId)
                .orElseThrow(() -> new ResourceNotFoundException("Case Not Found"));

        repository.delete(entity);

        return "Case Deleted Successfully";
    }
}