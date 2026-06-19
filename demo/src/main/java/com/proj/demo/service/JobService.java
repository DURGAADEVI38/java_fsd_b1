package com.proj.demo.service;

import com.proj.demo.dto.JobDto;
import com.proj.demo.mapper.JobMapper;
import com.proj.demo.model.Job;
import com.proj.demo.repo.JobRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepo jobRepo;
    private final JobMapper jobMapper;

    public List<JobDto> getAll(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        List<Job> jobs=jobRepo.findAll(pageable).getContent();
        return jobs
                .stream()
                .map(jobMapper::mapTojob)
                .toList();
    }
}
