package com.scriza.in.Wealth.User.User.Controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scriza.in.Wealth.BankDetails.Entity.Bank;
import com.scriza.in.Wealth.BankDetails.Service.BankService;
import com.scriza.in.Wealth.GlobalConfig.Response;
import com.scriza.in.Wealth.User.User.Entity.User;
import com.scriza.in.Wealth.User.User.Service.CustomUserDetails;
import com.scriza.in.Wealth.User.User.Service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BankService paymentAccountService;


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
    @GetMapping("/{userId}")
public ResponseEntity<Map<String, Object>> getUserById(@PathVariable String userId) {
    User user = userService.getUserByUserId(userId);

    if (user == null) {
        return Response.responseFailure("User not found.");
    }
    return Response.responseSuccess("User details retrieved successfully.", "user", user);
}
@PutMapping("/{userId}/updatePicture")
    public ResponseEntity<Map<String, Object>> updatePicture(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
        User updatedUser = userService.updateProfilePicture(userId, file);
        if (updatedUser == null) {
            return null;
        }
        return Response.responseSuccess("Profile picture updated successfully.", "user", updatedUser);
    }
    @GetMapping("/viewBank/{userId}")
    public ResponseEntity<?> viewBankDetailsByUserId(@PathVariable String userId) {

        List<Bank> bankDetails = paymentAccountService.getBankDetailsByUserId(userId);

        if (bankDetails == null) {
            return Response.responseFailure("Bank details not found for user ID: " + userId);
        }

        return Response.responseSuccess("Bank details retrieved successfully.", "bankDetails", bankDetails);
    }
     @PostMapping("/createBank/{userId}")
    public ResponseEntity<?> createOrUpdatePaymentAccount(@PathVariable String userId, @RequestBody Bank bankDetails) {
        User user = userService.getUserByUserId(userId); 
        if (user != null) {
            bankDetails.setUser(user); 
            return paymentAccountService.saveOrUpdatePaymentAccount(bankDetails);
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }
  
@PutMapping("/{userId}/bank/{bankId}")
public ResponseEntity<?> modifyBankDetails(@PathVariable String  userId, 
                                            @PathVariable Long bankId, 
                                            @RequestBody Bank updatedBankDetails) {
    return paymentAccountService.updateBankDetails(userId, bankId, updatedBankDetails);
}


}