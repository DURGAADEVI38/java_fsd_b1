package com.proj.demo.controller;

import com.proj.demo.dto.AppDto;
import com.proj.demo.dto.AppRespDto;
import com.proj.demo.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class SeekerController {
    private final ApplicationService applicationService;

    @PostMapping("/api/applications")
    public void createApplication(@Valid @RequestBody AppDto appDto)
    {
        applicationService.creatApplication(appDto);
    }
    @GetMapping("/api/my-applications")
    public List<AppRespDto> getmyApp(Principal principal,
                                     @RequestParam(defaultValue = "0", required = false)  int page,
                                     @RequestParam(defaultValue = "5", required = false)  int size)
    {
        String name=principal.getName();
        return applicationService.getmyapp(name,page,size);

    }
}
