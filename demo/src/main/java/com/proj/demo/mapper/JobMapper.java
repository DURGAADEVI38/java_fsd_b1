package com.proj.demo.mapper;

import com.proj.demo.dto.JobDto;
import com.proj.demo.model.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public JobDto mapTojob(Job job) {
        return new JobDto(job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getSalary(),
                job.getEmployer().getId());
    }

}
