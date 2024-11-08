package com.scriza.in.Wealth.User.User.Security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityFilterConfig {

    private JwtAuthenticationEntryPoint point;
    private JWTAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login.html","login.js", "/assets/**", "/css/**", "/js/**", "/images/**" , "/**").permitAll()  
                        .requestMatchers("/authenticate", "/register").permitAll()  
                        .requestMatchers("/user/**").hasRole("USER")  
                        .requestMatchers("/admin/**").hasRole("ADMIN")  
                        .anyRequest().authenticated())  
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))  
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)  
                .build();
    }
}