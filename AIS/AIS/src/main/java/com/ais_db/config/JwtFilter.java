package com.ais_db.config;

import com.ais_db.service.UserService;
import com.ais_db.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    public JwtFilter(
            JwtUtil jwtUtil,
            @org.springframework.context.annotation.Lazy UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // System.out.println("PATH = " + request.getServletPath());
        // System.out.println("AUTH HEADER = " + request.getHeader("Authorization"));

        logger.info("Incoming request path: {}", request.getRequestURI());

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ")) {

            jwt = authorizationHeader.substring(7);

            try {
                username = jwtUtil.extractUsername(jwt);

                logger.debug("JWT token successfully extracted from request");
                logger.debug("JWT (masked): {}...", jwt.substring(0, Math.min(10, jwt.length())));
                logger.info("Username extracted from token: {}", username);

            } catch (Exception e) {

                logger.error("JWT parsing failed: {}", e.getMessage(), e);
            }
        } else {
            logger.warn("Authorization header missing or invalid format");
        }

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    userService.loadUserByUsername(username);

            // System.out.println("AUTH = " + userDetails.getAuthorities());

            logger.info("Loading user details for: {}", username);
            logger.info("User authorities: {}", userDetails.getAuthorities());

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {

                logger.info("JWT validated successfully for user: {}", username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);

                logger.info("Security context updated for user: {}", username);

            } else {
                logger.warn("Invalid JWT token for user: {}", username);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.equals("/api/auth/login");
    }
}