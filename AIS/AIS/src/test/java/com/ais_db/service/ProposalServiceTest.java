package com.ais_db.service;


import com.ais_db.dto.ProposalDecisionDto;
import com.ais_db.enums.ProposalStatus;
import com.ais_db.exception.ResourceNotFoundException;
import com.ais_db.model.Proposal;
import com.ais_db.repository.ProposalRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProposalServiceTest {
    @Mock
    private ProposalRepo proposalRepo;
    @InjectMocks
    private ProposalService proposalService;


    private Proposal proposal;
    private Proposal proposal1;
    @BeforeEach
    public void sampleData()
    {
        proposal =new Proposal();
        proposal.setId(1);
        proposal.setRemarks("created");
        proposal.setProposalStatus(ProposalStatus.APPROVED);

        proposal1 =new Proposal();
        proposal1.setId(2);
        proposal1.setRemarks("proposal is created");

    }

    @Test
    public void getAllProposal_returnSomething()
    {
       when(proposalRepo.findAll()).thenReturn(List.of(proposal,proposal1));
        List<Proposal> actualCall = proposalService.getAll();

        assertThat(actualCall).hasSize(2);
        assertThat(actualCall.getFirst().getRemarks()).isEqualToIgnoringCase("created");
        assertThat(actualCall.get(1).getRemarks()).isEqualToIgnoringCase("proposal is created");
    }

    @Test
    public void getAllProposal_returnNothing()
    {
        when(proposalRepo.findAll()).thenReturn(List.of());
        List<Proposal> actualCall = proposalService.getAll();

        assertThat(actualCall).hasSize(0);

    }
    @Test
    public void getById_returnRemarks()
    {
        when(proposalRepo.findById(100)).thenReturn(Optional.of(proposal));
        assertThat(proposalService.getById(100).getId()).isEqualTo(1);
        assertThat(proposalService.getById(100).getRemarks()).isEqualToIgnoringCase("created");

    }
    @Test
    public void getById_returnNothing()
    {
        when(proposalRepo.findById(100)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> proposalService.getById(100))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Invalid id");
    }

    @Test
    public void deleteById_returnNothing()
    {
        when(proposalRepo.findById(100)).thenReturn(Optional.of(proposal));
        doNothing().when(proposalRepo).deleteById(100);
        proposalService.deleteById(100);
        verify(proposalRepo, times(1)).deleteById(100);
        verify(proposalRepo, times(1)).findById(100);
    }
    @Test
    public void makeDecision_updateProposal()
    {
        when(proposalRepo.findById(1)).thenReturn(Optional.of(proposal));
        ProposalDecisionDto dto = new ProposalDecisionDto(ProposalStatus.APPROVED,
                        "Approved by officer");
        proposalService.makeDecision(1, dto);
        verify(proposalRepo, times(1)).save(proposal);
        assertThat(proposal.getProposalStatus()).isEqualTo(ProposalStatus.APPROVED);


    }


}
