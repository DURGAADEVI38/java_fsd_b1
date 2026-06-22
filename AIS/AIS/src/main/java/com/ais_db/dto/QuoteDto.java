package com.ais_db.dto;

import com.ais_db.enums.AddOnType;
import com.ais_db.enums.QuoteStatus;

import java.time.LocalDate;
import java.util.List;

public record QuoteDto( Integer id,
                        Integer vehicleId,
                        double basePremium,
                        double vehicleTypeFactor,
                        double riskMultiplier,
                        double addonTotal,
                        double finalPremium,
                        LocalDate validTill,
                        List<AddOnType> addons,
                        QuoteStatus quoteStatus) {
}
