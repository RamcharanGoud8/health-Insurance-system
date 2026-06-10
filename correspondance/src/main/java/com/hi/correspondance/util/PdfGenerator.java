package com.hi.correspondance.util;


import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class PdfGenerator {

    public static String generatePdf(
            Integer caseId,
            String citizenName,
            String planName,
            String planStatus,
            String denialReason
    ) {

        String path = "/HIS_NOTICES/" +
                caseId + ".pdf";

        try {

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(path)
            );

            document.open();

            document.add(
                    new Paragraph(
                            "Health Insurance Notice"
                    )
            );

            document.add(
                    new Paragraph(
                            "Case ID : " + caseId
                    )
            );

            document.add(
                    new Paragraph(
                            "Citizen Name : " + citizenName
                    )
            );

            document.add(
                    new Paragraph(
                            "Plan Name : " + planName
                    )
            );

            document.add(
                    new Paragraph(
                            "Plan Status : " + planStatus
                    )
            );

            if ("DENIED".equals(planStatus)) {

                document.add(
                        new Paragraph(
                                "Reason : " + denialReason
                        )
                );
            }

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }
}
