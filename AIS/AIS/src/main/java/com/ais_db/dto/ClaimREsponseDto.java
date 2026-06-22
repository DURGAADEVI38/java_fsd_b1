package com.ais_db.dto;

import com.ais_db.enums.ClaimStatus;

import java.time.Instant;
import java.time.LocalDate;

public record ClaimREsponseDto(Integer id,
                               Integer customerPolicyId,
                               LocalDate accidentDate,
                               String accidentLocation,
                               Double estimatedLossAmount,
                               Double approvedAmount,
                               ClaimStatus claimStatus,
                               Instant createdAt) {
}
