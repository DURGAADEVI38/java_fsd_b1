package com.ais_db.dto;

import java.util.List;

public record ProposalREsponseV2(List<ProposalReqDto> data,
                                 int page,
                                 int size,
                                 long totalCount) {
}
