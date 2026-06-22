package com.ais_db.controller;

import com.ais_db.dto.*;
import com.ais_db.model.Claim;
import com.ais_db.service.ClaimService;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ClaimController {
    private final ClaimService claimService;

    //1.Creating a new claim
    @PostMapping(value = "/api/claim/create" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createClaim(@RequestPart("claim") ClaimDto claimDto,
                            @RequestPart(value = "files", required = false) List<MultipartFile> files,
                            Principal principal) throws IOException
    {
        String username= principal.getName();
        claimService.createClaim(claimDto,files,username);
    }

    //2.get the claim by id
    @GetMapping("/api/claim/get/{claimId}")
    public Claim getById(@PathVariable int claimId)
    {
        return claimService.getById(claimId);
    }
    //3.head
    @GetMapping("/api/claim/admin/all")
    public List<ClaimRespDto> getAllClaims(@RequestParam(defaultValue = "0", required = false)  int page,
                                           @RequestParam(defaultValue = "10", required = false)  int size) {
        return claimService.getAllClaims(page,size);
    }
    //4.Assign off
    @PostMapping("/api/claim/assign")
    public void assignClaim(@RequestBody ClaimAssignDto dto) {
        claimService.assignClaim(dto);
    }
//officer getting their assigned claims
    @GetMapping("/api/officer/claims")
    public List<AssignedClaimDto> getClaimsByOfficer(
            Principal principal,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {

        String officerName = principal.getName();

        return claimService.getClaimsByOfficer(page, size, officerName);
    }

    //3.Officer - approve claim
    @PutMapping("/api/claim/approve/{claimId}")
    public void approveClaim(@PathVariable int claimId,
                             @RequestBody ClaimDecisionDto dto) {
        claimService.approveClaim(claimId, dto);
    }
    //4.Officer - reject claim
    @PutMapping("/api/claim/reject/{claimId}")
    public void rejectClaim(@PathVariable int claimId,
                            @RequestBody ClaimDecisionDto dto) {
        claimService.rejectClaim(claimId, dto);
    }
    //5.settle claim
    @PutMapping("/api/claim/settle/{claimId}")
    public void settleClaim(@PathVariable int claimId) {
        claimService.settleClaim(claimId);
    }

    @GetMapping("/api/claim/my-claims")
    public List<ClaimREsponseDto> getMyClaims(Principal principal) {

        String username = principal.getName();
        return claimService.getMyClaims(username);
    }
    @GetMapping("/api/all/claims")
    public List<AllClaimsDto> allClaims()
    {
        return claimService.allClaims();

    }




}
