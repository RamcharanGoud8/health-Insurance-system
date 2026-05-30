package com.hi.correspondance.service;

import com.hi.correspondance.dto.CorrespondenceRequest;
import com.hi.correspondance.dto.CorrespondenceResponse;
import com.hi.correspondance.entity.Correspondence;
import com.hi.correspondance.repository.CorrespondenceRepository;
import com.hi.correspondance.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CorrespondenceServiceImpl implements CorrespondenceService {

    @Autowired
    private CorrespondenceRepository repository;

    @Override
    public CorrespondenceResponse generateNotice(
            CorrespondenceRequest request) {

        String pdfPath =
                PdfGenerator.generatePdf(
                        request.getCaseId(),
                        request.getCitizenName(),
                        request.getPlanName(),
                        request.getPlanStatus(),
                        request.getDenialReason()
                );

        Correspondence correspondence =
                new Correspondence();

        correspondence.setCaseId(
                request.getCaseId());

        correspondence.setCitizenName(
                request.getCitizenName());

        correspondence.setPlanName(
                request.getPlanName());

        correspondence.setPlanStatus(
                request.getPlanStatus());

        correspondence.setDenialReason(
                request.getDenialReason());

        correspondence.setGeneratedDate(
                LocalDate.now());

        correspondence.setNoticeStatus(
                "GENERATED");

        correspondence.setPdfPath(pdfPath);

        Correspondence saved =
                repository.save(correspondence);

        CorrespondenceResponse response =
                new CorrespondenceResponse();

        response.setNoticeId(
                saved.getNoticeId());

        response.setMessage(
                "Notice Generated Successfully");

        response.setPdfPath(pdfPath);

        return response;
    }
}