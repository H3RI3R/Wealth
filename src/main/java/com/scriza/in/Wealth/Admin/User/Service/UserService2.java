package com.scriza.in.Wealth.Admin.User.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.scriza.in.Wealth.GlobalConfig.Response;
import com.scriza.in.Wealth.User.User.Entity.User;
import com.scriza.in.Wealth.User.User.Repository.UserRepository;
@Service
public class UserService2 {

     @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> deleteUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return Response.responseFailure("User not found");
        }

        user.setStatus("blocked");
        userRepository.save(user);
        return Response.responseSuccess("User status updated to blocked", "userId", userId);
    }

}
