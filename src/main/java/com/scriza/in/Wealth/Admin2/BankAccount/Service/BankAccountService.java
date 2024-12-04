package com.scriza.in.Wealth.Admin2.BankAccount.Service;


import com.scriza.in.Wealth.Admin2.BankAccount.Entity.BankAccount2;
import com.scriza.in.Wealth.Admin2.BankAccount.Repository.BankAccountRepository;
import com.scriza.in.Wealth.GlobalConfig.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

     // Get All Active Bank Accounts/UPI Details
     public ResponseEntity<Map<String, Object>> getAllActiveBankAccounts() {
        List<BankAccount2> activeAccounts = bankAccountRepository.findAll().stream()
                .filter(bankAccount -> "Active".equalsIgnoreCase(bankAccount.getStatus()))
                .collect(Collectors.toList());

        return Response.responseSuccess("All active bank accounts/UPI details retrieved successfully.", "activeBankAccounts", activeAccounts);
    }
    



   // Create BankAccount/UPI Entry
   public ResponseEntity<Map<String, Object>> createBankAccount(BankAccount2 bankAccount) {
    // Set default status to Active if not provided
    if (bankAccount.getStatus() == null || bankAccount.getStatus().isEmpty()) {
        bankAccount.setStatus("Active");
    }

    // UPI validation
    if ("UPI".equalsIgnoreCase(bankAccount.getBankType())) {
        if (bankAccount.getUpiId() == null || bankAccount.getUpiName() == null || bankAccount.getUpiNumber() == null) {
            return Response.responseFailure("Please enter all UPI details (upiId, upiName, upiNumber).");
        }
        bankAccount.setBankId(null);
        bankAccount.setBankName(null);
        bankAccount.setBankIFSC(null);
        bankAccount.setBankAccountNumber(null);
    }
    // BankAccount validation
    else if ("BankAccount".equalsIgnoreCase(bankAccount.getBankType())) {
        if (bankAccount.getBankId() == null || bankAccount.getBankName() == null || bankAccount.getBankIFSC() == null || bankAccount.getBankAccountNumber() == null) {
            return Response.responseFailure("Please enter all BankAccount details (bankId, bankName, bankIFSC, bankAccountNumber).");
        }
        bankAccount.setUpiId(null);
        bankAccount.setUpiName(null);
        bankAccount.setUpiNumber(null);
    } else {
        return Response.responseFailure("Invalid BankType. Please select 'UPI' or 'BankAccount'.");
    }

    BankAccount2 savedAccount = bankAccountRepository.save(bankAccount);
    return Response.responseSuccess("Bank account/UPI added successfully.", "bankAccount", savedAccount);
}



    // Get All Bank Accounts/UPI Details
    public ResponseEntity<Map<String, Object>> getAllBankAccounts() {
        List<BankAccount2> bankAccounts = bankAccountRepository.findAll();
        return Response.responseSuccess("All bank accounts/UPI details retrieved successfully.", "bankAccounts", bankAccounts);
    }




    // Get BankAccount/UPI by ID
    public ResponseEntity<Map<String, Object>> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id)
                .map(account -> Response.responseSuccess("Bank account/UPI details retrieved successfully.", "bankAccount", account))
                .orElse(Response.responseFailure("Bank account/UPI not found!"));
    }




    // Update BankAccount/UPI
    public ResponseEntity<Map<String, Object>> updateBankAccount(Long id, BankAccount2 bankAccountDetails) {
        return bankAccountRepository.findById(id).map(existingAccount -> {
            if ("UPI".equalsIgnoreCase(bankAccountDetails.getBankType())) {
                if (bankAccountDetails.getUpiId() == null || bankAccountDetails.getUpiName() == null || bankAccountDetails.getUpiNumber() == null) {
                    return Response.responseFailure("Please enter all UPI details (upiId, upiName, upiNumber).");
                }
                existingAccount.setBankType("UPI");
                existingAccount.setUpiId(bankAccountDetails.getUpiId());
                existingAccount.setUpiName(bankAccountDetails.getUpiName());
                existingAccount.setUpiNumber(bankAccountDetails.getUpiNumber());
                existingAccount.setBankId(null);
                existingAccount.setBankName(null);
                existingAccount.setBankIFSC(null);
                existingAccount.setBankAccountNumber(null);
            } else if ("BankAccount".equalsIgnoreCase(bankAccountDetails.getBankType())) {
                if (bankAccountDetails.getBankId() == null || bankAccountDetails.getBankName() == null || bankAccountDetails.getBankIFSC() == null || bankAccountDetails.getBankAccountNumber() == null) {
                    return Response.responseFailure("Please enter all BankAccount details (bankId, bankName, bankIFSC, bankAccountNumber).");
                }
                existingAccount.setBankType("BankAccount");
                existingAccount.setBankId(bankAccountDetails.getBankId());
                existingAccount.setBankName(bankAccountDetails.getBankName());
                existingAccount.setBankIFSC(bankAccountDetails.getBankIFSC());
                existingAccount.setBankAccountNumber(bankAccountDetails.getBankAccountNumber());
                existingAccount.setUpiId(null);
                existingAccount.setUpiName(null);
                existingAccount.setUpiNumber(null);
            } else {
                return Response.responseFailure("Invalid BankType. Please select 'UPI' or 'BankAccount'.");
            }
            bankAccountRepository.save(existingAccount);
            return Response.responseSuccess("Bank account/UPI updated successfully.", "bankAccount", existingAccount);
        }).orElse(Response.responseFailure("Bank account/UPI not found!"));
    }





    // Delete BankAccount/UPI
    public ResponseEntity<Map<String, Object>> deleteBankAccount(Long id) {
        return bankAccountRepository.findById(id).map(account -> {
            bankAccountRepository.delete(account);
            return Response.responseSuccess("Bank account/UPI deleted successfully.", "id", id);
        }).orElse(Response.responseFailure("Bank account/UPI not found!"));
    }



    // Update Status of a Bank Account/UPI
public ResponseEntity<Map<String, Object>> updateBankAccountStatus(Long id, String status) {
    return bankAccountRepository.findById(id).map(existingAccount -> {
        if (!"Active".equalsIgnoreCase(status) && !"Inactive".equalsIgnoreCase(status)) {
            return Response.responseFailure("Invalid status. Please use 'Active' or 'Inactive'.");
        }
        existingAccount.setStatus(status);
        bankAccountRepository.save(existingAccount);
        return Response.responseSuccess("Bank account/UPI status updated successfully.", "bankAccount", existingAccount);
    }).orElse(Response.responseFailure("Bank account/UPI not found!"));
}
}