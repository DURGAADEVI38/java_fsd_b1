package com.ais_db.controller;

import com.ais_db.dto.ForgotPasswordDto;
import com.ais_db.dto.LoginRespDto;
import com.ais_db.dto.PasswordUpdateDto;
import com.ais_db.dto.TokenDto;
import com.ais_db.model.User;
import com.ais_db.service.UserInfoService;
import com.ais_db.service.UserService;
import com.ais_db.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserInfoService userInfoService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    //1.Login
    @GetMapping("/api/auth/login")
    public TokenDto login(Principal principal){
        logger.info("Login request received");

        String username = principal.getName();
        logger.info("User authenticated successfully: {}", username);

        String token = jwtUtil.generateToken(username);

        logger.debug("JWT token generated for user: {}", username);

        logger.info("Login process completed for user: {}", username);

        return new TokenDto(username, token);
    }

    //2.Getting Logged-in user details
    @GetMapping("/api/auth/user-details")
    public LoginRespDto getUserDetails(Principal principal){
        User user = (User)userService.loadUserByUsername(principal.getName());
        return new LoginRespDto(
                user.getId(),
                user.getUsername(),
                user.getRole().toString()
        );
    }
    //3.change password
    @PutMapping("/api/user/update-password")
    public void updatePassword(@Valid @RequestBody PasswordUpdateDto dto, Principal principal) {
        userInfoService.updatePassword(principal.getName(), dto.newPassword());
    }
    @PostMapping("/api/forgot-password")
    public String forgotPassword(
            @Valid @RequestBody ForgotPasswordDto dto
    ) {
        return userInfoService.forgotPassword(dto);
    }

}
