package com.hi.correspondance.service;

import com.hi.correspondance.dto.CorrespondenceRequest;
import com.hi.correspondance.dto.CorrespondenceResponse;
import com.hi.correspondance.entity.Correspondence;
import com.hi.correspondance.repository.CorrespondenceRepository;
import com.hi.correspondance.util.PdfGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CorrespondenceServiceImpl implements CorrespondenceService {

    private static final Logger log = LoggerFactory.getLogger(CorrespondenceServiceImpl.class);

    @Autowired
    private CorrespondenceRepository repository;

    @Override
    public CorrespondenceResponse generateNotice(CorrespondenceRequest request) {
        log.info("Generating notice: caseId={}, citizenName={}, planName={}, planStatus={}",
                request.getCaseId(), request.getCitizenName(),
                request.getPlanName(), request.getPlanStatus());

        String pdfPath;
        try {
            pdfPath = PdfGenerator.generatePdf(
                    request.getCaseId(),
                    request.getCitizenName(),
                    request.getPlanName(),
                    request.getPlanStatus(),
                    request.getDenialReason()
            );
            log.info("PDF generated successfully: caseId={}, pdfPath={}", request.getCaseId(), pdfPath);
        } catch (Exception e) {
            log.error("PDF generation failed: caseId={}, error={}", request.getCaseId(), e.getMessage(), e);
            throw e;
        }

        Correspondence correspondence = new Correspondence();
        correspondence.setCaseId(request.getCaseId());
        correspondence.setCitizenName(request.getCitizenName());
        correspondence.setPlanName(request.getPlanName());
        correspondence.setPlanStatus(request.getPlanStatus());
        correspondence.setDenialReason(request.getDenialReason());
        correspondence.setGeneratedDate(LocalDate.now());
        correspondence.setNoticeStatus("GENERATED");
        correspondence.setPdfPath(pdfPath);

        Correspondence saved = repository.save(correspondence);
        log.info("Correspondence record saved: noticeId={}, caseId={}, planStatus={}, noticeStatus={}",
                saved.getNoticeId(), saved.getCaseId(), saved.getPlanStatus(), saved.getNoticeStatus());

        CorrespondenceResponse response = new CorrespondenceResponse();
        response.setNoticeId(saved.getNoticeId());
        response.setMessage("Notice Generated Successfully");
        response.setPdfPath(pdfPath);

        return response;
    }
}
