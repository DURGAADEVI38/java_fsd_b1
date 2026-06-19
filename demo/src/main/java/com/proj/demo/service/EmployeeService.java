package com.proj.demo.service;

import com.proj.demo.dto.JobDto;
import com.proj.demo.model.Employer;
import com.proj.demo.model.Job;
import com.proj.demo.repo.EmployerRepo;
import com.proj.demo.repo.JobRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final JobRepo jobRepo;
    private final EmployerRepo employerRepo;

    public void createJobs(@Valid JobDto jobDto) {
        Employer employer = employerRepo.findById(jobDto.employerId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        Job job=new Job();
        job.setTitle(jobDto.title());
        job.setDescription(jobDto.description());
        job.setLocation(jobDto.location());
        job.setSalary(jobDto.salary());
        job.setEmployer(employer);
        jobRepo.save(job);

    }

}
