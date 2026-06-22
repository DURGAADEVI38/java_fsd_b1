package com.ais_db.dto;

public record ClaimReportDto( Integer claimId,
                              String riskLevel,
                              String stage,
                              String financialImpact,
                              String actionHint,
                              String slaStatus) {
}
