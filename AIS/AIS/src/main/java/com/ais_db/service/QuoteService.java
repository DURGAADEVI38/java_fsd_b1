package com.ais_db.service;

import com.ais_db.dto.QuoteDto;
import com.ais_db.dto.QuoteMapper;
import com.ais_db.enums.AddOnType;
import com.ais_db.enums.QuoteStatus;
import com.ais_db.exception.ResourceNotFoundException;
import com.ais_db.model.Policy;
import com.ais_db.model.Proposal;
import com.ais_db.model.Quote;
import com.ais_db.model.Vehicle;
import com.ais_db.repository.Policyrepo;
import com.ais_db.repository.ProposalRepo;
import com.ais_db.repository.QuoteRepo;
import com.ais_db.repository.VehicleRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class QuoteService {
    private final QuoteRepo quoteRepo;
    private final VehicleRepo vehicleRepo;
    private final Policyrepo policyrepo;


    public QuoteDto generateQuote(Integer vehicleId,Integer policyId, List<AddOnType> addons) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        Policy policy = policyrepo.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        // 1. Calculate vehicle age
        int currentYear = LocalDate.now().getYear();
        int age = currentYear - vehicle.getYearOfPurchase();

        // 2. Current value (simple depreciation logic)
        double currentValue = vehicle.getPurchasePrice() * Math.pow(0.9, age);

        // 3. Base premium logic
        double basePremium=policy.getBasePremium();

        double vehicleTypeFactor;
        switch (vehicle.getVehicleType()) {
            case BIKE -> vehicleTypeFactor = 1.0;
            case CAR -> vehicleTypeFactor = 1.3;
            case HEAVY_VEHICLE -> vehicleTypeFactor = 1.7;
            default -> vehicleTypeFactor = 1.0;
        }
        // Age risk (smooth curve)
        double ageRisk = 1 + (age * age) / 200.0;
        // Value factor (log scaling)
        double valueFactor = 1 + Math.log(currentValue / 100000.0 + 1) * 0.15;
        // Combined risk
        double riskMultiplier = vehicleTypeFactor * ageRisk * valueFactor;
        riskMultiplier = Math.round(riskMultiplier * 100.0) / 100.0;

    // Add-ons (percentage + flat mix)
        double addonTotal = 0;

        for (AddOnType addon : addons) {
            switch (addon) {
                case ZERO_DEPRECIATION -> addonTotal += basePremium * 0.10;
                case ENGINE_PROTECTION -> addonTotal += basePremium * 0.07;
                case PASSENGER_COVER -> addonTotal += 800;
                default -> addonTotal += 500;
            }
        }

// Final premium
        double finalPremium = (basePremium * riskMultiplier) + addonTotal;

        // 7. Create Quote
        Quote quote = new Quote();
        quote.setVehicle(vehicle);
        quote.setPolicy(policy);
        quote.setBasePremium(basePremium);
        quote.setVehicleTypeFactor(vehicleTypeFactor);
        quote.setRiskMultiplier(riskMultiplier);
        quote.setAddonTotal(addonTotal);
        quote.setFinalPremium(finalPremium);
        quote.setValidTill(LocalDate.now().plusDays(30));
        quote.setAddons(addons);
        quote.setQuoteStatus(QuoteStatus.GENERATED);

        quoteRepo.save(quote);

        return QuoteMapper.toDto(quote);
    }
}
