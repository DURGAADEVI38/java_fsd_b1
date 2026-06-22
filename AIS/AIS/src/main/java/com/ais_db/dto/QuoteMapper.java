package com.ais_db.dto;

import com.ais_db.model.Quote;
import org.springframework.stereotype.Component;


@Component
public class QuoteMapper {

    public static QuoteDto toDto(Quote quote) {
        return new QuoteDto(
                quote.getId(),
                quote.getVehicle().getId(),
                quote.getBasePremium(),
                quote.getVehicleTypeFactor(),
                quote.getRiskMultiplier(),
                quote.getAddonTotal(),
                quote.getFinalPremium(),
                quote.getValidTill(),
                quote.getAddons(),
                quote.getQuoteStatus()
        );
    }




}
