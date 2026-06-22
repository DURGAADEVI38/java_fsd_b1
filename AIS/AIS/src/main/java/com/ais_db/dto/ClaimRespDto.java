package com.ais_db.dto;

import com.ais_db.enums.ClaimStatus;

import java.time.LocalDate;

public record ClaimRespDto(int id,
                           LocalDate accidentDate,
                           String accidentLocation,
                           String accidentDescription,
                           double estimatedLossAmount,
                           double approvedAmount,
                           ClaimStatus claimStatus,

                           Integer customerPolicyId,
                           Integer insuranceOfficerId) {
}
