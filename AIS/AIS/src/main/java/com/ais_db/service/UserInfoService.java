package com.ais_db.service;

import com.ais_db.dto.ForgotPasswordDto;
import com.ais_db.dto.UserRespDto;
import com.ais_db.enums.Role;
import com.ais_db.mapper.UserMapper;
import com.ais_db.model.Customer;
import com.ais_db.model.User;
import com.ais_db.repository.CustomerRepo;
import com.ais_db.repository.UserRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepo customerRepo;
    private final UserRepo userRepo;


    //1.Adding customer in user db and customer db
    public void addUser(UserRespDto userRespDto) {
        User user= UserMapper.maptoEntity(userRespDto);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(userRespDto.password()));
        userService.save(user);
        Customer customer = new Customer();
        customer.setName(userRespDto.name());
        customer.setAddress(userRespDto.address());
        customer.setContactNumber(userRespDto.contactNumber());
        customer.setEmail(userRespDto.email());
        customer.setUser(user);
        customerRepo.save(customer);

    }

    public void updatePassword(String username, String newPassword) {

        String encoded = passwordEncoder.encode(newPassword);
        userService.updatePassword(username,encoded);
    }

    public String forgotPassword(@Valid ForgotPasswordDto dto) {
        User user = userRepo.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(dto.newPassword()));

        userRepo.save(user);

        return "Password updated successfully";
    }

}
