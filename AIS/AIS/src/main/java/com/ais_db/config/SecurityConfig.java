package com.ais_db.config;

import com.ais_db.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize

                        //AuthController
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/login").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/auth/user-details").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/user/update-password").authenticated()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/forgot-password").permitAll()

                        //User Controller
                        .requestMatchers(HttpMethod.POST,"/api/user/add").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/vehicle/register").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"api/customer/my-vehicles").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"api/generate/{vehicleId}").hasAuthority("CUSTOMER")

                        //Proposal Controller
                        .requestMatchers(HttpMethod.GET,"/api/policy/all").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/proposal/add").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/head/proposals").hasAuthority("HEAD")
                        .requestMatchers(HttpMethod.PUT,"/api/head/proposal/assign").hasAuthority("HEAD")
                        .requestMatchers(HttpMethod.GET,"/api/officer/proposals").hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.PUT,"/api/officer/proposals/*/decision").hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.GET,"/api/proposal/status/*").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/proposal/user").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/v2/user").permitAll()
                        .requestMatchers(HttpMethod.POST,"/buy/{quoteId}").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/proposal/status").permitAll()

                        //Insurance Controller
                        .requestMatchers(HttpMethod.POST,"/api/head/add").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/officer/add").hasAuthority("HEAD")
                        .requestMatchers(HttpMethod.POST,"/api/policy/create").hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.GET,"/api/policy").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/policy/update/{actCount}/{claimCount}").hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.GET,"/all/customer").hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.GET,"/all/officer").hasAuthority("HEAD")
                        .requestMatchers(HttpMethod.GET,"/reports").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/pie/vehicles").hasAuthority("OFFICER")

                        //Claim Controller
                        .requestMatchers(HttpMethod.POST,"/api/claim/create").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/claim/my-claims").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/claim/get/*").hasAnyAuthority("CUSTOMER","OFFICER")
                        .requestMatchers(HttpMethod.GET,"/api/claim/admin/all").hasAuthority("HEAD")
                        .requestMatchers(HttpMethod.GET,"/api/officer/claims").hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.POST,"/api/claim/approve/*").hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.POST,"/api/claim/reject/*").hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.POST,"/api/claim/settle/*").hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.POST,"/api/policy/renew/confirm").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/policy/renewal/quote/{customerPolicyId}").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/all/claims").hasAuthority("HEAD")

                        //Documet Controller
                        .requestMatchers(HttpMethod.POST,"/api/proposal/*/upload").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/proposal/*/documents").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/claim/*/documents").permitAll()

                                .anyRequest().authenticated()

                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
