package com.hi.correspondance.service;


import com.hi.correspondance.dto.CorrespondenceRequest;
import com.hi.correspondance.dto.CorrespondenceResponse;

public interface CorrespondenceService {

    CorrespondenceResponse generateNotice(CorrespondenceRequest request);
}
