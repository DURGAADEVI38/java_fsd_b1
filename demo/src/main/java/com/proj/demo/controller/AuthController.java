package com.proj.demo.controller;

import com.proj.demo.dto.EmployerDto;
import com.proj.demo.dto.SeekerDto;
import com.proj.demo.dto.AuthRespDto;
import com.proj.demo.service.AuthService;
import com.proj.demo.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @PostMapping("/api/employer/add")
    public void addEmp(@Valid@RequestBody EmployerDto employerDto)
    {
        authService.addEmployer(employerDto);
    }
    @PostMapping("/api/seeker/add")
    public void addJobSeeker(@Valid@RequestBody SeekerDto SeekerDto)
    {
        authService.addJobSeeker(SeekerDto);
    }
    @GetMapping("/api/login")
    public AuthRespDto login(Principal principal)
    {
        String username=principal.getName();
        String token= jwtUtil.generateToken(username);
        return new AuthRespDto(username,token);

    }





}
