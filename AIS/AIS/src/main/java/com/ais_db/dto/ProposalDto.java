package com.ais_db.dto;

import com.ais_db.enums.PaymentStatus;
import com.ais_db.enums.ProposalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;


public record ProposalDto(@NotNull(message = "Field sohuld not be empty")
                          @NotBlank(message = "Field sohuld not be empty")
                          String vehicle,
                          @NotNull(message = "Policy ID is required")
                          Integer policyId,
                          @NotNull(message = "User ID is required")
                          Integer userId,
                          @NotNull(message = "Field sohuld not be empty")
                          ProposalStatus proposalStatus
                           ) {

}
