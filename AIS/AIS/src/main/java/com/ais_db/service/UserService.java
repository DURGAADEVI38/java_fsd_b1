package com.ais_db.service;

import com.ais_db.model.User;
import com.ais_db.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class UserService implements UserDetailsService {
    private final UserRepo userRepo;



    public UserDetails loadUserByUsername(String username)
    {
        User user=userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid"));
        return user;
    }

    public User save(User user) {
        return userRepo.save(user);
    }


    public void updatePassword(String username, String encoded) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(encoded);
        userRepo.save(user);
    }
}
