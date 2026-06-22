package com.ais_db.dto;

import com.ais_db.enums.PaymentStatus;
import com.ais_db.enums.ProposalStatus;

public record ProposalReqDto( int proposalId,
                              ProposalStatus proposalStatus,
                              String remarks,
                              PaymentStatus paymentStatus
) {
}
