package com.ais_db.controller;

import com.ais_db.dto.PolicyrenewalDo;
import com.ais_db.dto.RenewalConfirmDto;
import com.ais_db.service.PolicyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class Policycontroller {
    private final PolicyService policyService;

    @GetMapping("/api/policy/renewal/quote/{customerPolicyId}")
    public PolicyrenewalDo getRenewalQuote(@PathVariable int customerPolicyId) {
        return policyService.calculateRenewalAmount(customerPolicyId);
    }
    @PostMapping("/api/policy/renew/confirm")
    public void confirmRenewal(@RequestBody RenewalConfirmDto dto, Principal principal)
    {
        policyService.confirmRenewal(dto, principal.getName());
    }
}
