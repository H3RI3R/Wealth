package com.scriza.in.Wealth.Admin2.BankAccount.Controller;


import com.scriza.in.Wealth.Admin2.BankAccount.Entity.BankAccount2;
import com.scriza.in.Wealth.Admin2.BankAccount.Service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/bankAccounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    // Create BankAccount/UPI
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createBankAccount(@RequestBody BankAccount2 bankAccount) {
        return bankAccountService.createBankAccount(bankAccount);
    }

    // Get All BankAccounts/UPI
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllBankAccounts() {
        return bankAccountService.getAllBankAccounts();
    }
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getAllActiveBankAccounts() {
        return bankAccountService.getAllActiveBankAccounts();
    }

    // Get BankAccount/UPI by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getBankAccountById(@PathVariable Long id) {
        return bankAccountService.getBankAccountById(id);
    }


//Update the status of the Account
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateBankAccountStatus(@PathVariable Long id, @RequestParam String status) {
        return bankAccountService.updateBankAccountStatus(id, status);
    }



    // Update BankAccount/UPI
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateBankAccount(@PathVariable Long id, @RequestBody BankAccount2 bankAccount) {
        return bankAccountService.updateBankAccount(id, bankAccount);
    }

    // Delete BankAccount/UPI
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteBankAccount(@PathVariable Long id) {
        return bankAccountService.deleteBankAccount(id);


    }
}