package com.scriza.in.Wealth.Admin.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/try")
    @PreAuthorize("hasRole('ADMIN')") // Specify that only users with 'ADMIN' role can access this method
    public String adminAccess(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hello " + userDetails.getUsername() + ", you have accessed the admin API.";
    }
}
