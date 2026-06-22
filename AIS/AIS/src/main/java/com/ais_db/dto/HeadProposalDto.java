package com.ais_db.dto;

import com.ais_db.enums.ProposalStatus;
import com.ais_db.model.Vehicle;

public record HeadProposalDto(int proposalId,
                              String customerName,
                              String vehicleNumber,
                              double finalPremium,
                              ProposalStatus proposalStatus) {
}
