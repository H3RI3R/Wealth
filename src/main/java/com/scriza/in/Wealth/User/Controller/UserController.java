package com.scriza.in.Wealth.User.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scriza.in.Wealth.User.Service.CustomUserDetails;

@RestController
@RequestMapping("/user")
public class UserController {

    // Simple endpoint to check authentication and access
    @GetMapping("/try")
    public String userTry(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("working till here"+3);
        try{
            CustomUserDetails myUserDetails = (CustomUserDetails) userDetails;
            return "Hello " + myUserDetails.getUser().getEmail()  + ", you have accessed the user API.";
        }catch(Exception e){
            System.out.println("failed in try ");
        }
        return "cheking if worked";

    }

}