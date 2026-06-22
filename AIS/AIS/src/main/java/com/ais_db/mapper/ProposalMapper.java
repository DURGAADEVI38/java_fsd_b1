package com.ais_db.mapper;

import com.ais_db.dto.*;
import com.ais_db.model.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProposalMapper {


    public ProposalRespDto getAllPage(Page<Proposal> pages) {
        Long totSize= pages.getTotalElements();
        int totPages= pages.getSize();
        List<Proposal> lists=pages.getContent();
        ProposalRespDto proposalRespDto =new ProposalRespDto(totSize,totPages,lists);
        return proposalRespDto;
    }


    public AssignedProposalDto getOfficerProposalDto(Proposal proposal) {

        return new AssignedProposalDto(proposal.getId(),
                proposal.getVehicle().getRegistrationNumber(),
                proposal.getVehicle().getModel(),
                proposal.getVehicle().getBrand(),
                proposal.getCustomer().getUser().getUsername(),
                proposal.getInsuranceOfficer().getName(),
                proposal.getQuote().getFinalPremium(),
                proposal.getProposalStatus(),
                proposal.getPaymentStatus(),
                proposal.getUploadedDocument()
        );
    }

    public ProposalReqDto getUserProposalDto(Proposal proposal) {
        return new ProposalReqDto(proposal.getId(),
                proposal.getProposalStatus(),
                proposal.getRemarks(),
                proposal.getPaymentStatus());
    }

    public HeadProposalDto getAllProposalDto(Proposal proposal) {
        return new HeadProposalDto(proposal.getId(),
                proposal.getVehicle().getRegistrationNumber(),
                proposal.getCustomer().getUser().getUsername(),
                proposal.getQuote().getFinalPremium(),
                proposal.getProposalStatus()
                );
    }
}
