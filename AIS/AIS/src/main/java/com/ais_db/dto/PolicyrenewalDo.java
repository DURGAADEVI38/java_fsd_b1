package com.ais_db.dto;

import java.time.LocalDate;

public record PolicyrenewalDo( int customerPolicyId,
                               LocalDate currentExpiryDate,
                               LocalDate newStartDate,
                               LocalDate newExpiryDate,
                               Double renewalAmount) {
}
