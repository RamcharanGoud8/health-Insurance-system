package com.hi.eligibilitydetermination.feign;

import com.hi.eligibilitydetermination.dto.BenefitRequestDTO;
import com.hi.eligibilitydetermination.dto.BenefitResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BENEFIT-ISSUANCE-SERVICE")
public interface BenefitFeignClient {

    @PostMapping("/benefits/issue")
    BenefitResponseDTO issueBenefit(@RequestBody BenefitRequestDTO request);
}
