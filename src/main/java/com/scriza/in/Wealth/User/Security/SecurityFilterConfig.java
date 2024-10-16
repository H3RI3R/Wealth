package com.scriza.in.Wealth.User.Security;


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
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authenticate", "/register").permitAll()  // Permit register and login (authentication)
                        .requestMatchers("/user/**").hasRole("USER")  // Access to user routes only for users
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Access to admin routes only for admin
                        .anyRequest().authenticated())  // All other requests should be authenticated
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))  // Handle unauthorized access
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // No session, stateless
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)  // Add JWT filter
                .build();
    }
}