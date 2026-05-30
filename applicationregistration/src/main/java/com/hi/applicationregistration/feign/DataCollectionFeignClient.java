package com.hi.applicationregistration.feign;


import com.hi.applicationregistration.dto.ApplicationToDataCollectionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DATA-COLLECTION-SERVICE")
public interface DataCollectionFeignClient {

    @PostMapping("/api/data-collection")
    String saveData(@RequestBody ApplicationToDataCollectionRequest request
    );
}
