package com.scriza.in.Wealth.Admin.User.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scriza.in.Wealth.Admin.User.Service.UserService2;



@RestController
@RequestMapping("/api/admin")
public class UserController2 {
  @Autowired
    private UserService2 userService;

    @PutMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }
   
}


