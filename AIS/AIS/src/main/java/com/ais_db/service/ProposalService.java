package com.ais_db.service;

import com.ais_db.dto.*;
import com.ais_db.enums.PaymentStatus;
import com.ais_db.enums.ProposalStatus;
import com.ais_db.exception.ResourceNotFoundException;
import com.ais_db.mapper.ProposalMapper;
import com.ais_db.model.*;
import com.ais_db.repository.*;
import com.ais_db.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProposalService {
    private final ProposalRepo proposalRepo;
    private  final ProposalMapper proposalMapper;
    private final InsuranceOfficerService insuranceOfficerService;
    private final QuoteRepo quoteRepo;
    private final CustomerRepo customerRepo;
    private final PolicyService policyService;

    private final DocumentRepo documentRepo;
    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<Proposal> getAll()
    {
        return proposalRepo.findAll();
    }

    public Proposal getById(int id)
    {
        return proposalRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid id"));
    }

    public void deleteById(int id)
    {
        getById(id);
        proposalRepo.deleteById(id);
    }

    public ProposalRespDto getAllPage(int pg, int sz) {
        Pageable pageable= PageRequest.of(pg, sz);
        Page<Proposal> pages=proposalRepo.findAll(pageable);
        return proposalMapper.getAllPage(pages);


    }public void buyPolicy(int quoteId,
                           MultipartFile file,
                           String username) throws IOException {

        Quote quote = quoteRepo.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        Customer customer = customerRepo.getByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Proposal proposal = new Proposal();

        proposal.setQuote(quote);
        proposal.setPolicy(quote.getPolicy());
        proposal.setVehicle(quote.getVehicle());
        proposal.setCustomer(customer);
        proposal.setProposalStatus(ProposalStatus.INITIATED);
        proposal.setPaymentStatus(PaymentStatus.PENDING);

        Proposal savedProposal = proposalRepo.save(proposal);

        List<String> fileNames = new ArrayList<>();

        if (file != null && !file.isEmpty()) {

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            FileUtil.validateFile(file);

            String fileName = System.currentTimeMillis()
                                + "_"
                            + file.getOriginalFilename();

            Path destinationPath = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    destinationPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            fileNames.add(fileName);

            Document document = new Document();
            document.setFileName(fileName);
            document.setFilePath(destinationPath.toString());
            document.setProposal(savedProposal);

            documentRepo.save(document);
        }

        if (!fileNames.isEmpty()) {
            savedProposal.setUploadedDocument(
                    String.join(",", fileNames)
            );

            proposalRepo.save(savedProposal);
        }
    }

    public List<HeadProposalDto> getInitiatedProposals(int page,int size) {
        Pageable pageable=PageRequest.of(page,size);
        List<Proposal> proposals=proposalRepo.getbyProposalStatus(
                ProposalStatus.INITIATED,pageable).getContent();
        return proposals.stream()
                .map(proposalMapper:: getAllProposalDto)
                .toList();

    }

    public void assignOfficer(AssignOfficerDto dto) {
        Proposal proposal = proposalRepo.findById(dto.proposalId())
                .orElseThrow(() ->
                        new RuntimeException("Proposal not found"));
        InsuranceOfficer officer = insuranceOfficerService.getById(dto.officerId());
        proposal.setInsuranceOfficer(officer);
        proposal.setProposalStatus(ProposalStatus.OFFICER_ASSIGNED);
        proposalRepo.save(proposal);
    }
    public List<AssignedProposalDto> getProposalByOfficer(int page, int size, String officerName) {
        Pageable pageable=PageRequest.of(page,size, Sort.by("createdAt").descending());
        InsuranceOfficer officer =
                insuranceOfficerService.findByUsername(officerName);
        Page<Proposal> proposals = proposalRepo.findByOfficerId(officer.getId(), pageable);
        return proposals
                .stream()
                .map(proposalMapper::getOfficerProposalDto)
                .toList();
    }

    public void makeDecision(int proposalId, ProposalDecisionDto dto) {

        Proposal proposal = proposalRepo.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        proposal.setProposalStatus(dto.proposalStatus());
        proposal.setRemarks(dto.remarks());

        proposalRepo.save(proposal);


    }




    public List<ProposalReqDto> getProposalbyUser(int page,int size,String username) {
        Pageable pageable=PageRequest.of(page,size);
        List<Proposal> proposals = proposalRepo.getByUserName(username,pageable).getContent();
        System.out.println("Proposal count = " + proposals.size());

        proposals.forEach(p ->
                System.out.println(
                        "Proposal ID = " + p.getId()
                ));

        return proposals.stream()
                .map(proposalMapper:: getUserProposalDto)
                .toList();
    }


    public CustomerPolicy makePayment(int proposalId) {
        Proposal proposal = proposalRepo.findById(proposalId)
                .orElseThrow(() ->
                        new RuntimeException("Proposal not found"));
        if (proposal.getProposalStatus() != ProposalStatus.APPROVED) {
            throw new RuntimeException(
                    "Payment can be made only for approved proposals");
        }
        if (proposal.getPaymentStatus() == PaymentStatus.PAID) {
            throw new RuntimeException("Payment already completed");
        }
        proposal.setPaymentStatus(PaymentStatus.PAID);
        proposalRepo.save(proposal);
        //policy creation
        return policyService.createCustomerPolicy(proposal);
        }


    public ChartResponse getProposalStatusChart() {
        List<ProposalStatus> statuses =
                Arrays.stream(ProposalStatus.values()).toList();

        List<Object[]> result = proposalRepo.getProposalStatusCount();

        Map<String, Long> map = new HashMap<>();

        for (Object[] row : result) {
            map.put(row[0].toString(), ((Number) row[1]).longValue());
        }

        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();

        for (ProposalStatus status : statuses) {
            labels.add(status.name());
            data.add(map.getOrDefault(status.name(), 0L));
        }

        return new ChartResponse(
                labels,
                data,
                "Proposal Status Distribution"
        );
    }

    public long countByUser(String username) {
        return proposalRepo.countByCustomer_User_Username(username);
    }
}

