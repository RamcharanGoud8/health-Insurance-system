package com.hi.correspondance.controller;

import com.hi.correspondance.dto.CorrespondenceRequest;
import com.hi.correspondance.dto.CorrespondenceResponse;
import com.hi.correspondance.service.CorrespondenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/correspondence")
public class CorrespondenceController {

    @Autowired
    private CorrespondenceService service;

    @PostMapping("/generate")
    public CorrespondenceResponse generateNotice(@RequestBody CorrespondenceRequest request) {

        return service.generateNotice(request);
    }
}
