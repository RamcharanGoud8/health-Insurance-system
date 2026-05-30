package com.hi.eligibilitydetermination.feign;

import com.hi.eligibilitydetermination.dto.CorrespondenceRequestDTO;
import com.hi.eligibilitydetermination.dto.CorrespondenceResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CORRESPONDENCE-SERVICE")
public interface CorrespondenceFeignClient {

    @PostMapping("/correspondence/generate")
    CorrespondenceResponseDTO generateNotice(@RequestBody CorrespondenceRequestDTO request);
}