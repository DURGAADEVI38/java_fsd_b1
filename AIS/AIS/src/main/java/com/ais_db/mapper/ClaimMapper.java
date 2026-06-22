package com.ais_db.mapper;

import com.ais_db.dto.AssignedClaimDto;
import com.ais_db.dto.ClaimDto;
import com.ais_db.dto.ClaimREsponseDto;
import com.ais_db.dto.ClaimRespDto;
import com.ais_db.model.Claim;
import com.ais_db.model.Proposal;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class ClaimMapper {
    public Claim maptoclaim(@Valid ClaimDto claimDto) {

        Claim claim=new Claim();
        claim.setAccidentDate(claimDto.accidentDate());
        claim.setAccidentLocation(claimDto.accidentLocation());
        claim.setEstimatedLossAmount(claimDto.estimatedLossAmount());
        return claim;

    }

    public ClaimRespDto toDto(Claim claim) {
        return new ClaimRespDto(
                claim.getId(),
                claim.getAccidentDate(),
                claim.getAccidentLocation(),
                claim.getAccidentDescription(),
                claim.getEstimatedLossAmount(),
                claim.getApprovedAmount(),
                claim.getClaimStatus(),
                claim.getCustomerPolicy() != null ? claim.getCustomerPolicy().getId() : null,
                claim.getInsuranceOfficer() != null ? claim.getInsuranceOfficer().getId() : null
                );
    }

    public ClaimREsponseDto mapToResponseDto(Claim claim) {
        return new ClaimREsponseDto(
                claim.getId(),
                claim.getCustomerPolicy().getId(),
                claim.getAccidentDate(),
                claim.getAccidentLocation(),
                claim.getEstimatedLossAmount(),
                claim.getApprovedAmount(),
                claim.getClaimStatus(),
                claim.getCreatedAt()
        );
    }
    public static AssignedClaimDto toAssignedClaimDto(Claim c) {
        return new AssignedClaimDto(
                c.getId(),
                c.getCustomerPolicy().getId(),
                c.getCustomerPolicy().getCustomer().getName(),
                c.getAccidentLocation(),
                c.getApprovedAmount(),
                c.getEstimatedLossAmount(),
                c.getClaimStatus(),
                c.getUploadedDocument()
        );
    }
}

