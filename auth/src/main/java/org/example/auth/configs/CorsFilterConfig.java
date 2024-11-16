package org.example.auth.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class CorsFilterConfig {

    @Bean
    public OncePerRequestFilter corsFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        };
    }
}
