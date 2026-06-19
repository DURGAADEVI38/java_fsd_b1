package com.proj.demo.mapper;

import com.proj.demo.dto.AppDto;
import com.proj.demo.dto.AppRespDto;
import com.proj.demo.model.Application;
import com.proj.demo.model.Job;
import com.proj.demo.model.Seeker;
import com.proj.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {
    public Application maptoApp(AppDto appDto, Job job, Seeker seeker) {
        Application application=new Application();
        application.setJob(job);
        application.setAppliedAt(appDto.appliedAt());
        application.setJob(job);
        application.setSeeker(seeker);
        return application;

    }

    public static AppRespDto maptoUser(Application application) {
        return new AppRespDto(application.getJob().getId(),
                application.getAppliedAt(),
                application.getJob().getTitle(),
                application.getJob().getEmployer().getCompanyName()
                );
    }

}
