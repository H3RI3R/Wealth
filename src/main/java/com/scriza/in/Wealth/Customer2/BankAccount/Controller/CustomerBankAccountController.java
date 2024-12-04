package com.scriza.in.Wealth.Customer2.BankAccount.Controller;


import com.scriza.in.Wealth.Customer2.BankAccount.Entity.CustomerBankAccount;
import com.scriza.in.Wealth.Customer2.BankAccount.Service.CustomerBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer/bankAccounts")
public class CustomerBankAccountController {

    @Autowired
    private CustomerBankAccountService customerBankAccountService;

    // Add Customer Bank Account
    @PostMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> addBankAccount(
            @PathVariable String userId, @RequestBody CustomerBankAccount bankAccount) {
        return customerBankAccountService.addCustomerBankAccount(userId, bankAccount);
    }

    // Get All Customer Bank Accounts
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBankAccounts() {
        return customerBankAccountService.getAllCustomerBankAccounts();
    }

    // Get Customer Bank Account by ID
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getBankAccountByUserId(@PathVariable String userId) {
        return customerBankAccountService.getCustomerBankAccountsByUserId(userId);
    }

    // Update Customer Bank Account

    @PutMapping("/{userId}/{bankId}")
    public ResponseEntity<Map<String, Object>> updateBankAccount(
            @PathVariable String userId,
            @PathVariable Long bankId,
            @RequestBody CustomerBankAccount updatedAccount)  {
    return customerBankAccountService.updateBankAccount(userId,bankId, updatedAccount);
}
//delete bank
@DeleteMapping("/{userId}/{bankId}")
public ResponseEntity<Map<String, Object>> deleteBankAccount(
        @PathVariable String userId, 
        @PathVariable Long bankId) {
    return customerBankAccountService.deleteCustomerBankAccount(userId, bankId);
}

    // Change the status of a Customer Bank Account
@PutMapping("/status/{userId}/{bankId}")
public ResponseEntity<Map<String, Object>> changeBankAccountStatus(
        @PathVariable String userId, @PathVariable Long bankId) {
    return customerBankAccountService.changeBankAccountStatus(userId, bankId);
}
    // Get Active Admin Bank Accounts
    @GetMapping("/admin/active")
    public ResponseEntity<Map<String, Object>> getAdminActiveBankAccounts() {
        return customerBankAccountService.getAdminActiveBankAccounts();
    }
}