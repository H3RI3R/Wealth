package com.scriza.in.Wealth.User.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    // Simple endpoint to check authentication and access
    @GetMapping("/try")
    public String userTry(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hello " + userDetails.getUsername() + ", you have accessed the user API.";
    }

}