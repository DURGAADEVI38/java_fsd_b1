package com.ais_db.service;

import com.ais_db.model.CustomerPolicy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostPolicyService {

    private final PdfService pdfService;
    public void handlePolicyPostActions(CustomerPolicy policy) {

        // 1. Generate PDF
        byte[] pdf = pdfService.generatePolicyPdf(policy);

    }

}
