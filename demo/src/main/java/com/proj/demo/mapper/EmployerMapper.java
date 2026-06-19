package com.proj.demo.mapper;

import com.proj.demo.dto.EmployerDto;
import com.proj.demo.dto.SeekerDto;
import com.proj.demo.model.Employer;
import com.proj.demo.model.Seeker;
import com.proj.demo.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {
    public User maptoUser(@Valid EmployerDto employerDto) {

        User user=new User();
        user.setUsername(employerDto.name());
        return user;
    }

    public Employer maptoEmp(@Valid EmployerDto employerDto) {
        Employer employer=new Employer();
        employer.setUsername(employerDto.name());
        employer.setCompanyName(employerDto.companyName());
        return  employer;
    }

    public User maptoUserInfo(@Valid SeekerDto seekerDto) {
        User user=new User();
        user.setUsername(seekerDto.username());
        return user;
    }

    public Seeker mapToSeeker(@Valid SeekerDto seekerDto) {
        Seeker seeker=new Seeker();
        seeker.setName(seekerDto.username());
        seeker.setResumeSummary(seekerDto.resumeSummary());
        return seeker;
    }
}
