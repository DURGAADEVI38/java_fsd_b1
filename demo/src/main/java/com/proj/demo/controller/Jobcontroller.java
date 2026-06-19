package com.proj.demo.controller;

import com.proj.demo.dto.JobDto;
import com.proj.demo.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class Jobcontroller {
    private final JobService jobService;

    @GetMapping("/api/jobs/all")
    public List<JobDto> getAllJobs(@RequestParam(defaultValue = "0", required = false)  int page,
                                   @RequestParam(defaultValue = "5", required = false)  int size)
    {
       return jobService.getAll(page,size);
    }
}
