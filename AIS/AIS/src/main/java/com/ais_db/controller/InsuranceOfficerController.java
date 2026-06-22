package com.ais_db.controller;

import com.ais_db.dto.*;
import com.ais_db.model.Claim;
import com.ais_db.model.Customer;
import com.ais_db.model.Policy;
import com.ais_db.repository.ClaimRepo;
import com.ais_db.repository.CustomerRepo;
import com.ais_db.service.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class InsuranceOfficerController {
    private final InsuranceOfficerService insuranceOfficerService;
    private final PolicyService policyService;
    private final ClaimReportService reportService;
    private final CustomerService customerService;
    private final ClaimRepo claimRepo;
    private final VehicleService vehicleService;

    //1.Adding head in the system
    @PostMapping("/api/head/add")
    public void addHead(@Valid @RequestBody HeadRespDto headRespDto)
    {
        insuranceOfficerService.addHead(headRespDto);
    }

    //2.Head-Add officer to in the system
    @PostMapping("/api/officer/add")
    public void addOff(@Valid @RequestBody OfficerRespoDto officerRespoDto)
    {

            insuranceOfficerService.addOff(officerRespoDto);

    }
    //3.Officer-Create new policy
    @PostMapping("/api/policy/create")
    public void createPolicy(@RequestBody PolicyDto policyDto)
    {

        System.out.println("DTO RECEIVED: " + policyDto);
        insuranceOfficerService.createPolicy(policyDto);
    }
    @GetMapping("/api/policy")
    public Page<PolicyDto> getPolicies(@RequestParam int page, @RequestParam int size) {

        return policyService.getPolicies(page, size);
    }

    //4.Update the policy details
    @PutMapping("/api/policy/update/{policyID}/{actCount}/{claimCount}")
    public Policy updatePolicy(@PathVariable int policyID,@PathVariable int actCount,
                               @PathVariable int claimCount)
    {
        return insuranceOfficerService.updatePolicy(policyID,actCount,claimCount);
    }

    //5.Get policy
    @GetMapping("/api/v1/policies/{policyId}")
    public Policy getPolicyById(@PathVariable int policyId) {
        return insuranceOfficerService.getPolicyById(policyId);
    }

    //6.DeletePolicy
    @DeleteMapping("/api/v1/policies/{policyId}")
    public void deletePolicyById(@PathVariable int policyId) {
        insuranceOfficerService.deletePolicyById(policyId);
    }

    @GetMapping("/all/customer")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/all/officer")
    public List<OfficerListDto> getAllOfficers() {
        return insuranceOfficerService.getAllOfficers();
    }

    @GetMapping("/reports")
    public List<ClaimReportDto> getReports() {
        List<Claim> claims = claimRepo.findAll();

        System.out.println("CLAIMS SIZE = " + claims.size());

        return claimRepo.findAll()
                .stream()
                .map(reportService::buildReport)
                .toList();
    }
    @GetMapping("/api/pie/vehicles")
    public List<PiechartDto> getVehicleTypeDistribution() {
        return vehicleService.getVehicleTypeDistribution();
    }


}
