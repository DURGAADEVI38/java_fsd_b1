package com.ais_db.dto;

import java.time.LocalDate;

public record RenewalConfirmDto(int customerPolicyId,
                                Double paidAmount,
                                LocalDate newStartDate,
                                LocalDate newExpiryDate) {
}
