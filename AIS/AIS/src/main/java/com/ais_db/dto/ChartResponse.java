package com.ais_db.dto;

import com.ais_db.enums.ProposalStatus;

import java.util.List;

public record ChartResponse(List<String> label,
                            List<Long> data,
                            String title
                            ) {
}
