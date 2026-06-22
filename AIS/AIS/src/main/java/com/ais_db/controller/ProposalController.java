package com.ais_db.controller;

import com.ais_db.dto.*;
import com.ais_db.model.CustomerPolicy;
import com.ais_db.model.Policy;
import com.ais_db.repository.CustomerPolicyRepo;

import com.ais_db.service.PdfService;
import com.ais_db.service.PolicyService;
import com.ais_db.service.ProposalService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProposalController {
    private final ProposalService proposalService;
    private final PolicyService policyService;
    private final CustomerPolicyRepo customerPolicyRepo;
    private final PdfService pdfService;

    //1.Public user can see the policies which are available in the system
    @GetMapping("/api/policy/all")
    public List<Policy> getAll()
    {
        return  policyService.getAll();
    }

    //2.Customer-Buy policy:create a proposal
    @PostMapping("/buy/{quoteId}")
    public void  buyPolicy(@PathVariable int quoteId,
                           @RequestParam("file") MultipartFile document,
                           Principal principal) throws IOException
    {
        String username= principal.getName();
        proposalService.buyPolicy(quoteId,document,username);
    }
    //3.Head-view all initiated proposal
    @GetMapping("/api/head/proposals")
    public List<HeadProposalDto> getInitiatedProposals( @RequestParam(defaultValue = "0", required = false)  int page,
                                                        @RequestParam(defaultValue = "10", required = false)  int size) {

        return proposalService.getInitiatedProposals(page,size);
    }
    //4.Head-Assiging officer to proposal
    @PutMapping("/api/head/proposal/assign")
    public void assignOfficer(@RequestBody AssignOfficerDto dto) {

        proposalService.assignOfficer(dto);
    }
    //5.Officer-get assigned to the proposal
    @GetMapping("/api/officer/proposals")
    public List<AssignedProposalDto> getProposalByOfficerId(Principal principal,
                                                            @RequestParam(defaultValue = "0", required = false)  int page,
                                                            @RequestParam(defaultValue = "10", required = false)  int size) {

        String officerName=principal.getName();
        return proposalService.getProposalByOfficer(page,size,officerName);
    }

    //6.Officer Decision
    @PutMapping("/api/officer/proposals/{proposalId}/decision")
    public void makeDecision(@PathVariable int proposalId,
            @Valid @RequestBody ProposalDecisionDto dto) {

        proposalService.makeDecision(proposalId,dto);


    }

    //7.User-Checking their status of the proposal
    @GetMapping("/api/proposal/user/")
    public List<ProposalReqDto> getProposalbyUser(Principal principal,
                                                @RequestParam(defaultValue = "0", required = false)  int page,
                                                @RequestParam(defaultValue = "10", required = false)  int size)
    {
        String username= principal.getName();
        return proposalService.getProposalbyUser(page,size,username);
    }
    @GetMapping("/v2/user")
    public ProposalREsponseV2 getProposalByUserV2(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        String username = principal.getName();
        List<ProposalReqDto> proposals = proposalService.getProposalbyUser(page, size, username);
        long totalCount = proposalService.countByUser(username);
        return new ProposalREsponseV2(proposals, page, size, totalCount);
    }



    //8.User- payment
    @PostMapping("/api/payment/{proposalId}")
    public ApproveRespDto makePayment(@PathVariable int proposalId) {
        CustomerPolicy policy =proposalService.makePayment(proposalId);
        return new ApproveRespDto(
                policy.getId(),
                policy.getCustomer().getEmail()
        );
    }

    @GetMapping("/api/policy/{id}/pdf")
    public ResponseEntity<byte[]> getPdf(@PathVariable int id) {

        CustomerPolicy policy = customerPolicyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        byte[] pdf = pdfService.generatePolicyPdf(policy);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=policy.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/proposal/status")
    public ChartResponse getProposalStatus() {
        return proposalService.getProposalStatusChart();
    }



    //8.Pagination
    @GetMapping("/api/proposal/getv")
    public ProposalRespDto getAllPage(@RequestParam int pg,@RequestParam int sz)
    {
        return proposalService.getAllPage(pg,sz);
    }

    //9.Deleting the proposal
    @DeleteMapping("/api/proposal/delete/{id}")
    public void deleteById(@PathVariable int id)
    {
            proposalService.deleteById(id);
    }













}
