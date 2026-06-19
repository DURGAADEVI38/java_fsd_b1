package com.proj.demo.controller;

import com.proj.demo.dto.JobDto;
import com.proj.demo.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class Employeecontroller {

    private final EmployeeService employeeService;

    @PostMapping("/api/jobs")
    public void createJobs(@Valid @RequestBody JobDto jobDto)
    {
        employeeService.createJobs(jobDto);
    }

}
