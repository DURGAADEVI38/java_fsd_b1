package com.ais_db.service;

import com.ais_db.dto.ClaimReportDto;
import com.ais_db.model.Claim;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClaimReportService {

    public ClaimReportDto buildReport(Claim c) {

        // 1. RISK CALCULATION
        String risk;
        double amount = c.getEstimatedLossAmount();

        if (amount >= 50000) {
            risk = "HIGH RISK ";
        } else if (amount >= 10000) {
            risk = "MEDIUM RISK ";
        } else {
            risk = "LOW RISK ";
        }

        // 2. STAGE
        String stage = switch (c.getClaimStatus().name()) {
            case "OFFICER_ASSIGNED" -> "UNDER REVIEW";
            case "APPROVED" -> "SETTLED";
            case "REJECTED" -> "REJECTED";
            default -> "PENDING";
        };

        // 3. FINANCIAL IMPACT
        String financial = "₹" + amount + " EXPOSURE";

        // 4. SLA (simple example)
        String sla = (amount > 20000)
                ? "SLA CRITICAL "
                : "WITHIN SLA";

        // 5. ACTION HINT
        String action;
        if (c.getUploadedDocument() == null) {
            action = "MISSING DOCUMENT ";
        } else if (c.getClaimStatus().name().equals("OFFICER_ASSIGNED")) {
            action = "UNDER REVIEW";
        } else {
            action = "NO ACTION";
        }

        return new ClaimReportDto(
                c.getId(),
                risk,
                stage,
                financial,
                action,
                sla
        );
    }
}

