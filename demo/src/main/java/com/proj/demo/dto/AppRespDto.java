package com.proj.demo.dto;

import java.time.LocalDate;

public record AppRespDto(Integer jobId,
                         LocalDate appliedAt,
                         String jobTitle,
                         String companyName) {
}
