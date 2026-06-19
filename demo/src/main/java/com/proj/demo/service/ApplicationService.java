package com.proj.demo.service;

import com.proj.demo.dto.AppDto;
import com.proj.demo.dto.AppRespDto;
import com.proj.demo.mapper.ApplicationMapper;
import com.proj.demo.model.Application;
import com.proj.demo.model.Job;
import com.proj.demo.model.Seeker;
import com.proj.demo.model.User;
import com.proj.demo.repo.ApplicationRepo;
import com.proj.demo.repo.JobRepo;
import com.proj.demo.repo.SeekerRepo;
import com.proj.demo.repo.UserRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService {
    private final JobRepo jobRepo;
    private final UserRepo userRepo;
    private final ApplicationRepo applicationRepo;
    private final ApplicationMapper applicationMapper;
    private final SeekerRepo seekerRepo;

    public void creatApplication(AppDto appDto) {
        Job job=jobRepo.findById(appDto.jobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));
        Seeker seeker=seekerRepo.findById(appDto.seekerId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Application application=applicationMapper.maptoApp(appDto,job,seeker);
        applicationRepo.save(application);
    }

    public List<AppRespDto> getmyapp(String name, int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        List<Application> applications=applicationRepo.getByUsername(name,pageable).getContent();
        return applications
                .stream()
                .map(ApplicationMapper:: maptoUser)
                .toList();


    }
    /*
    List<Proposal> proposals = proposalRepo.getByUserName(username,pageable).getContent();


        return proposals.stream()
                .map(proposalMapper:: getUserProposalDto)
                .toList();
     */
}
