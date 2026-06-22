package com.ais_db.service;

import com.ais_db.dto.DocumentDto;
import com.ais_db.enums.ProposalStatus;
import com.ais_db.mapper.DocumentMapper;
import com.ais_db.model.Document;
import com.ais_db.model.Proposal;
import com.ais_db.repository.DocumentRepo;
import com.ais_db.repository.ProposalRepo;
import com.ais_db.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final ProposalRepo proposalRepo;
    private final DocumentRepo documentRepo;
    @Value("${file.upload-dir}")
    private String uploadDir;
    public void upload(int proposalId, MultipartFile file) throws IOException {

        Proposal proposal = proposalRepo.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));

        FileUtil.validateFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path destinationPath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        Document document = new Document();
        document.setFileName(fileName);   
        document.setFilePath(destinationPath.toString());
        document.setProposal(proposal);
        documentRepo.save(document);
        proposal.setProposalStatus(ProposalStatus.UNDER_REVIEW);
        proposalRepo.save(proposal);
    }

    public List<DocumentDto> getDocuments(int proposalId) {
        return documentRepo.findByProposal_Id(proposalId)
                .stream()
                .map(DocumentMapper::toDto)
                .toList();
    }

    public List<DocumentDto> getDocumentsClaims(int claimId) {
        return documentRepo.findByClaimId(claimId)
                .stream()
                .map(DocumentMapper::toDto)
                .toList();
    }
}
