package com.proj.demo.service;

import com.proj.demo.dto.EmployerDto;
import com.proj.demo.dto.SeekerDto;
import com.proj.demo.enums.Role;
import com.proj.demo.mapper.EmployerMapper;
import com.proj.demo.model.Employer;
import com.proj.demo.model.Seeker;
import com.proj.demo.model.User;
import com.proj.demo.repo.EmployerRepo;
import com.proj.demo.repo.SeekerRepo;
import com.proj.demo.repo.UserRepo;
import com.proj.demo.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmployerMapper employerMapper;
    private final EmployerRepo employerRepo;
    private final SeekerRepo seekerRepo;


    public void addEmployer(@Valid EmployerDto employerDto) {
        User user=employerMapper.maptoUser(employerDto);
        Employer employer=employerMapper.maptoEmp(employerDto);
        user.setRole(Role.EMPLOYER);
        user.setPassword(passwordEncoder.encode(employerDto.password()));
        user=userService.save(user);
        employerRepo.save(employer);
    }

    public void addJobSeeker(@Valid SeekerDto seekerDto) {
        User user=employerMapper.maptoUserInfo(seekerDto);
        Seeker seeker=employerMapper.mapToSeeker(seekerDto);
        user.setRole(Role.JOB_SEEKER);
        user.setPassword(passwordEncoder.encode(seekerDto.password()));
        user=userService.save(user);
        seekerRepo.save(seeker);
    }

/*

    public void addJobSeeker(@Valid SeekerDto authDto) {
        User user=bookMapper.maptoUser(authDto);
        Employe employe=bookMapper.maptoEmp(authDto);
        user.setRole(Role.OFFICER);
        user.setPassword(passwordEncoder.encode(authDto.password()));
        user=userService.save(user);
        employe.setUser(user);
        employeerepo.save(employe);

    }

 */


}
