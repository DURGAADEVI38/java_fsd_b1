package com.ais_db.service;

import com.ais_db.dto.*;
import com.ais_db.enums.ClaimStatus;
import com.ais_db.enums.ProposalStatus;
import com.ais_db.exception.ResourceNotFoundException;
import com.ais_db.mapper.ClaimMapper;
import com.ais_db.model.*;
import com.ais_db.repository.*;
import com.ais_db.util.FileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimService {
    private final ClaimRepo claimRepo;
    private final ClaimMapper claimMapper;
    private final CustomerPolicyRepo customerPolicyRepo;
    private final InsuranceOfficerRepo insuranceOfficerRepo;
    private final DocumentRepo documentRepo;
    @Value("${file.upload-dir}")
    private String uploadDir;
    private final InsuranceOfficerService insuranceOfficerService;

    public void createClaim(@Valid ClaimDto claimDto,
                            List<MultipartFile> files,
                            String username) throws IOException {

        List<CustomerPolicy> policies = customerPolicyRepo
                .findPoliciesByUsername(username);

        CustomerPolicy customerPolicy = policies.stream()
                .filter(p -> p.getId() == claimDto.customerPolicyId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Policy not owned by user"));

        Claim claim = claimMapper.maptoclaim(claimDto);
        claim.setCustomerPolicy(customerPolicy);
        claim.setApprovedAmount(0.0);
        claim.setClaimStatus(ClaimStatus.PENDING);
        Claim savedClaim = claimRepo.save(claim);
        List<String> fileNames = new ArrayList<>();

        if (files != null && !files.isEmpty()) {

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile file : files) {

                FileUtil.validateFile(file); // validate each file

                String originalName = file.getOriginalFilename();
                String fileName = System.currentTimeMillis() + "_" + originalName;

                Path destinationPath = uploadPath.resolve(fileName);

                Files.copy(file.getInputStream(),
                        destinationPath,
                        StandardCopyOption.REPLACE_EXISTING);
                fileNames.add(fileName);

                Document document = new Document();
                document.setFileName(fileName);
                document.setFilePath(destinationPath.toString());
                document.setClaim(savedClaim);

                documentRepo.save(document);

            }
        }
        if (!fileNames.isEmpty()) {
            savedClaim.setUploadedDocument(String.join(",", fileNames));
            claimRepo.save(savedClaim);
        }

    }

    public Claim getById(int claimId) {
        return claimRepo.findById(claimId).orElseThrow(() -> new ResourceNotFoundException("Invalid id"));
    }
    public List<ClaimRespDto> getAllClaims(int page,int size) {
        Pageable pageable=PageRequest.of(page,size);
        List<Claim> claims=claimRepo.getbyClaimStatus(
                ClaimStatus.PENDING,pageable).getContent();

        return claims.stream()
                .map(claimMapper::toDto)
                .toList();
    }

    public void assignClaim(ClaimAssignDto dto) {
        Claim claim = claimRepo.findById(dto.claimId())
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
        InsuranceOfficer officer = insuranceOfficerRepo.findById(dto.officerId())
                .orElseThrow(() -> new ResourceNotFoundException("Officer not found"));
        if (claim.getClaimStatus() != ClaimStatus.PENDING) {
            throw new RuntimeException("Only PENDING claims can be assigned");
        }

        claim.setInsuranceOfficer(officer);
        claim.setClaimStatus(ClaimStatus.OFFICER_ASSIGNED);

        claimRepo.save(claim);
    }

    public void approveClaim(int claimId, ClaimDecisionDto dto) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        if (claim.getClaimStatus() != ClaimStatus.OFFICER_ASSIGNED) {
            throw new RuntimeException("Only Assigned claims can be approved");
        }

        claim.setApprovedAmount(dto.approvedAmount());
        claim.setClaimStatus(ClaimStatus.APPROVED);
        claimRepo.save(claim);
    }

    public void rejectClaim(int claimId, ClaimDecisionDto dto) {

            Claim claim = claimRepo.findById(claimId)
                    .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

            if (claim.getClaimStatus() != ClaimStatus.UNDER_REVIEW) {
                throw new RuntimeException("Only UNDER_REVIEW claims can be rejected");
            }

            claim.setClaimStatus(ClaimStatus.REJECTED);
            claim.setApprovedAmount(0);
            claimRepo.save(claim);
    }

    public void settleClaim(int claimId) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
        if (claim.getClaimStatus() != ClaimStatus.APPROVED) {
            throw new RuntimeException("Only APPROVED claims can be settled");
        }
        
        claim.setClaimStatus(ClaimStatus.SETTLED);
        claimRepo.save(claim);
    }

    public List<ClaimREsponseDto> getMyClaims(String username) {
        List<Claim> claims = claimRepo.findClaimsByUsername(username);

        return claims.stream()
                .map(claimMapper::mapToResponseDto)
                .toList();
    }

    public List<AssignedClaimDto> getClaimsByOfficer(int page, int size, String officerName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        InsuranceOfficer officer =
                insuranceOfficerService.findByUsername(officerName);


        Page<Claim> claims = claimRepo.findByInsuranceOfficerId(officer.getId(), pageable);

        return claims.stream()
                .map(ClaimMapper::toAssignedClaimDto)
                .toList();
    }

    public List<AllClaimsDto> allClaims() {
        return claimRepo.findAll()
                .stream()
                .map(c -> new AllClaimsDto(
                        c.getId(),
                        c.getInsuranceOfficer() != null
                                ? c.getInsuranceOfficer().getId()
                                : null,
                        c.getClaimStatus(),
                        c.getCreatedAt() != null ? c.getCreatedAt().toString() : null
                ))
                .toList();
    }
}
