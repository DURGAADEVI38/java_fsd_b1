package com.ais_db.dto;

import com.ais_db.enums.ClaimStatus;

public record AssignedClaimDto(Integer claimId,
                               Integer policyNumber,
                               String customerName,
                               String accidentLocation,
                               double approvedAmount,
                               double estimatedLossAmount,
                               ClaimStatus claimStatus,
                               String uploadedDocument) {
}
