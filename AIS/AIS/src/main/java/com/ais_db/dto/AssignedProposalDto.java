package com.ais_db.dto;

import com.ais_db.enums.PaymentStatus;
import com.ais_db.enums.ProposalStatus;

public record AssignedProposalDto( int proposalId,
                                   String registrationNumber,
                                   String model,
                                   String brand,
                                   String customerName,
                                   String officerName,
                                   double finalPremium,
                                   ProposalStatus proposalStatus,
                                   PaymentStatus paymentStatus,
                                   String uploadedDocument) {
}
