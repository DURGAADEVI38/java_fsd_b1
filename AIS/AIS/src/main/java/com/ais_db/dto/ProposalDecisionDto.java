package com.ais_db.dto;

import com.ais_db.enums.ProposalStatus;
import jakarta.validation.constraints.NotNull;

public record ProposalDecisionDto(@NotNull(message = "Status is required")
                                  ProposalStatus proposalStatus,

                                  String remarks) {
}
