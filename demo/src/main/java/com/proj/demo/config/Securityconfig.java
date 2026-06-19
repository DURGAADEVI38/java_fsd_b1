package com.proj.demo.config;

import com.proj.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class Securityconfig {
    private final JwtFilter jwtFilter;
    private final UserService userService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.POST,"/api/employer/add").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/seeker/add").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/login").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/jobs").hasAuthority("EMPLOYER")
                        .requestMatchers(HttpMethod.GET,"/api/jobs/all").hasAnyAuthority("EMPLOYER","JOB_SEEKER")
                        .requestMatchers(HttpMethod.POST,"/api/applications").hasAnyAuthority("JOB_SEEKER")
                        .requestMatchers(HttpMethod.POST,"/api/create/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/my-applications").hasAuthority("JOB_SEEKER")

                        .anyRequest().authenticated()
                );

        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userService);
        dao.setUserDetailsService(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    }
