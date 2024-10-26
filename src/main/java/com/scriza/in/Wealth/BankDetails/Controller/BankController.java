package com.scriza.in.Wealth.BankDetails.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scriza.in.Wealth.BankDetails.Entity.Bank;
import com.scriza.in.Wealth.BankDetails.Service.BankService;


@RestController
@RequestMapping("/api/admin/bank")
public class BankController {


    @Autowired
    private BankService paymentAccountService;
    // @PostMapping("/create/{userId}")
    // public ResponseEntity<?> createOrUpdatePaymentAccount(@PathVariable String userId, @RequestBody Bank bankDetails) {
    //     User user = userService.getUserByUserId(userId); 
    //     if (user != null) {
    //         bankDetails.setUser(user); 
    //         return paymentAccountService.saveOrUpdatePaymentAccount(bankDetails);
    //     } else {
    //         return ResponseEntity.badRequest().body("User not found");
    //     }
    // }


    // @GetMapping("/user/{userId}")
    // public ResponseEntity<?> getAllPaymentAccountsByUserId(@PathVariable String userId) {
    //     return paymentAccountService.getAllPaymentAccountsByUserId(userId);
    // }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentAccountById(@PathVariable Long id) {
        return paymentAccountService.getPaymentAccountById(id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentAccount(@PathVariable Long id) {
        return paymentAccountService.deletePaymentAccount(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePaymentAccount(@PathVariable Long id, @RequestBody Bank paymentAccount) {
        return paymentAccountService.updatePaymentAccount(id, paymentAccount);
    }

}