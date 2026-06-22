package com.ais_db.controller;

import com.ais_db.dto.DocumentDto;
import com.ais_db.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DocumentController {
    private final DocumentService documentService;

    //1.Requesting documents to upload
    @PostMapping("/api/proposal/{proposalId}/upload")
    public void upload(@PathVariable int proposalId,
                       @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("UPLOAD STARTED");

        documentService.upload(proposalId, file);
    }
    //2.getting the uploaded documents
    @GetMapping("/api/proposal/{proposalId}/documents")
    public List<DocumentDto> getDocuments(
            @PathVariable int proposalId) {

        return documentService.getDocuments(proposalId);
    }
    //get the claim doc
    @GetMapping("/api/claim/{claimId}/documents")
    public List<DocumentDto> getDocumentsofClaim(
            @PathVariable int claimId) {

        return documentService.getDocumentsClaims(claimId);
    }
}
