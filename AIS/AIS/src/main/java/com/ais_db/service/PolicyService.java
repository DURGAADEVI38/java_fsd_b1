package com.ais_db.service;

import com.ais_db.dto.PolicyDto;
import com.ais_db.dto.PolicyrenewalDo;
import com.ais_db.dto.RenewalConfirmDto;
import com.ais_db.enums.PolicyStatus;
import com.ais_db.exception.ResourceNotFoundException;
import com.ais_db.mapper.PolicyMapper;
import com.ais_db.model.CustomerPolicy;
import com.ais_db.model.Policy;
import com.ais_db.model.Proposal;
import com.ais_db.repository.CustomerPolicyRepo;
import com.ais_db.repository.Policyrepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PolicyService {
    private final Policyrepo policyrepo;
    private final CustomerPolicyRepo customerPolicyRepo;
   

    public List<Policy> getAll() {
        return policyrepo.findAll();
    }



    public CustomerPolicy createCustomerPolicy(Proposal proposal) {
        CustomerPolicy customerPolicy=new CustomerPolicy();
        customerPolicy.setCustomer(proposal.getCustomer());
        customerPolicy.setPolicy(proposal.getPolicy());
        customerPolicy.setProposal(proposal);
        customerPolicy.setVehicle(proposal.getVehicle());
        customerPolicy.setPurchaseDate(LocalDate.now());
        customerPolicy.setStartDate(LocalDate.now());
        customerPolicy.setExpiryDate(LocalDate.now().plusYears(1));
        customerPolicy.setStatus(PolicyStatus.ACTIVE);
        CustomerPolicy savedPolicy=customerPolicyRepo.save(customerPolicy);
        return savedPolicy;
        }

        

    public PolicyrenewalDo calculateRenewalAmount(int customerPolicyId) {

        CustomerPolicy policy = customerPolicyRepo.findById(customerPolicyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        // Allow renewal only within 30 days of expiry
        long daysLeft = ChronoUnit.DAYS.between(
                LocalDate.now(),
                policy.getExpiryDate()
        );

        if (daysLeft > 30) {
            throw new RuntimeException(
                    "Policy is not eligible for renewal yet. Renewal is allowed only within 30 days of expiry."
            );
        }

        int currentYear = LocalDate.now().getYear();
        int policyAge = currentYear - policy.getStartDate().getYear();

        LocalDate newStart = policy.getExpiryDate().plusDays(1);
        LocalDate newExpiry = newStart.plusYears(1);

        double basePremium;

        switch (policy.getVehicle().getVehicleType()) {
            case BIKE -> basePremium = 5000;
            case CAR -> basePremium = 9000;
            case HEAVY_VEHICLE -> basePremium = 15000;
            default -> basePremium = 9000;
        }

        double riskMultiplier = (policyAge > 3) ? 1.2 : 1.0;
        double inflationMultiplier = Math.pow(1.05, policyAge);

        double renewalAmount =
                (basePremium * riskMultiplier) * inflationMultiplier;

        return new PolicyrenewalDo(
                policy.getId(),
                policy.getExpiryDate(),
                newStart,
                newExpiry,
                (double) Math.round(renewalAmount)
        );
    }

    public void confirmRenewal(RenewalConfirmDto dto, String name) {
        CustomerPolicy policy = customerPolicyRepo.findById(dto.customerPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        // 2. Validate user ownership
        if (!policy.getCustomer().getUser().getUsername().equals(name)) {
            throw new RuntimeException("Unauthorized access");
        }

        // 3. Validate payment amount (important safety check)
        Double systemQuote = calculateRenewalAmount(dto.customerPolicyId()).renewalAmount();

        if (Math.abs(systemQuote - dto.paidAmount()) > 0.01) {
            throw new RuntimeException("Payment amount mismatch with quote");
        }

        // 4. Update policy after successful payment
        policy.setStartDate(dto.newStartDate());
        policy.setExpiryDate(dto.newExpiryDate());
        policy.setStatus(PolicyStatus.ACTIVE);

        // 5. Save updated policy
        customerPolicyRepo.save(policy);
    }

    public Page<PolicyDto> getPolicies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return policyrepo.findAll(pageable)
                .map(PolicyMapper::toDto);
    }
}
