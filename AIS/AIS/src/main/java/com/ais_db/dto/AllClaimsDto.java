package com.ais_db.dto;

import com.ais_db.enums.ClaimStatus;

public record AllClaimsDto(Integer id,
                           Integer insuranceOfficerId,
                           ClaimStatus claimStatus,
                           String  createdAt) {
}
