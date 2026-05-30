package com.hi.datacollection.feign;


import com.hi.datacollection.dto.ApplicationSyncDTO;
import com.hi.datacollection.dto.EligibilityRequestDTO;
import com.hi.datacollection.dto.EligibilityResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ELIGIBILITY-DETERMINATION-SERVICE")
public interface EligibilityFeignClient {

    @PostMapping("/api/eligibility")
    EligibilityResponseDTO determineEligibility(@RequestBody EligibilityRequestDTO request);
}
