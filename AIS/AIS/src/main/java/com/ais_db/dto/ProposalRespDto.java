package com.ais_db.dto;

import com.ais_db.model.Proposal;

import java.util.List;

public record ProposalRespDto(
       long totalPages,
       int totalRecords,
       List<Proposal> lists
) {
}
