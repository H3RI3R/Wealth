package com.scriza.in.Wealth.Customer2.BankAccount.Service;


import com.scriza.in.Wealth.Customer2.BankAccount.Entity.CustomerBankAccount;
import com.scriza.in.Wealth.Customer2.BankAccount.Repository.CustomerBankAccountRepository;
import com.scriza.in.Wealth.Admin2.BankAccount.Repository.BankAccountRepository;
import com.scriza.in.Wealth.Admin2.BankAccount.Entity.BankAccount2;
import com.scriza.in.Wealth.GlobalConfig.Response;
import com.scriza.in.Wealth.User.User.Entity.User;
import com.scriza.in.Wealth.User.User.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class CustomerBankAccountService {

    @Autowired
    private CustomerBankAccountRepository customerBankAccountRepository;

    @Autowired
    private BankAccountRepository adminBankAccountRepository;
     @Autowired
    private UserRepository userRepository;

    // Create or Add Customer Bank Account
   public ResponseEntity<Map<String, Object>> addCustomerBankAccount(String userId, CustomerBankAccount bankAccount) {
    // Check if the User exists
    User user = userRepository.findByUserId(userId);
    if (user == null) {
        return Response.responseFailure("User with userId " + userId + " not found!");
    }

    // Associate the user with the bank account
    bankAccount.setUser(user);  // Link the bank account to the user

    // Validate bank details based on bankType
    if ("UPI".equalsIgnoreCase(bankAccount.getBankType())) {
        if (bankAccount.getUpiId() == null || bankAccount.getUpiName() == null || bankAccount.getUpiNumber() == null) {
            return Response.responseFailure("Please provide all UPI details (upiId, upiName, upiNumber).");
        }
        bankAccount.setBankName(null);
        bankAccount.setBankIFSC(null);
        bankAccount.setBankAccountNumber(null);
    } else if ("BankAccount".equalsIgnoreCase(bankAccount.getBankType())) {
        if (bankAccount.getBankName() == null || bankAccount.getBankIFSC() == null || bankAccount.getBankAccountNumber() == null) {
            return Response.responseFailure("Please provide all BankAccount details (bankName, bankIFSC, bankAccountNumber).");
        }
        bankAccount.setUpiId(null);
        bankAccount.setUpiName(null);
        bankAccount.setUpiNumber(null);
    } else {
        return Response.responseFailure("Invalid bankType. Please provide 'UPI' or 'BankAccount'.");
    }

    // Save the bank account
    CustomerBankAccount savedAccount = customerBankAccountRepository.save(bankAccount);

    // Simplified response with just success message
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Bank account/UPI added successfully.");
    response.put("bankAccount", Map.of(
            "bankType", savedAccount.getBankType(),
            "status", savedAccount.getStatus()
    ));

    return ResponseEntity.ok(response);
}

    // Get All Customer Bank Accounts
    public ResponseEntity<Map<String, Object>> getAllCustomerBankAccounts() {
        List<CustomerBankAccount> accounts = customerBankAccountRepository.findAll();
        return Response.responseSuccess("All customer bank accounts retrieved successfully.", "customerBankAccounts", accounts);
    }

    public ResponseEntity<Map<String, Object>> getCustomerBankAccountsByUserId(String userId) {
        // Check if the User exists
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return Response.responseFailure("User with userId " + userId + " not found!");
        }
    
        // Get all bank accounts for the user
        List<CustomerBankAccount> accounts = customerBankAccountRepository.findByUser(user);
        if (accounts.isEmpty()) {
            return Response.responseFailure("No bank accounts/UPIs found for userId: " + userId);
        }
    
        // Create the simplified response with bankId
        List<Map<String, Object>> bankAccountsResponse = new ArrayList<>();
        for (CustomerBankAccount account : accounts) {
            Map<String, Object> bankAccountData = new HashMap<>();
            bankAccountData.put("bankId", account.getId());  // Include the bank ID in the response
            bankAccountData.put("bankType", account.getBankType());
            bankAccountData.put("status", account.getStatus());
    
            // Add additional fields based on bankType if needed
            if ("UPI".equalsIgnoreCase(account.getBankType())) {
                bankAccountData.put("upiId", account.getUpiId());
                bankAccountData.put("upiName", account.getUpiName());
                bankAccountData.put("upiNumber", account.getUpiNumber());
            } else if ("BankAccount".equalsIgnoreCase(account.getBankType())) {
                bankAccountData.put("bankName", account.getBankName());
                bankAccountData.put("bankIFSC", account.getBankIFSC());
                bankAccountData.put("bankAccountNumber", account.getBankAccountNumber());
            }
    
            bankAccountsResponse.add(bankAccountData);
        }
    
        // Return the response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bank accounts/UPIs retrieved successfully.");
        response.put("bankAccounts", bankAccountsResponse);
    
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<Map<String, Object>> updateBankAccount(String userId, Long bankId, CustomerBankAccount updatedAccount) {

        // Step 1: Check if the User exists
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return Response.responseFailure("User with userId " + userId + " not found!");
        }
    
        // Step 2: Find the bank account by bankId
        CustomerBankAccount existingAccount = customerBankAccountRepository.findById(bankId).orElse(null);
        if (existingAccount == null) {
            return Response.responseFailure("Bank account with bankId " + bankId + " not found!");
        }
    
        // Step 3: Ensure the bank account belongs to the given userId
        if (!existingAccount.getUser().getUserId().equals(userId)) {
            return Response.responseFailure("Bank account does not belong to the user with userId " + userId);
        }
    
        // Step 4: Update fields based on the request body
        if (updatedAccount.getBankType() != null) {
            existingAccount.setBankType(updatedAccount.getBankType());
        }
        if (updatedAccount.getStatus() != null) {
            existingAccount.setStatus(updatedAccount.getStatus());
        }
    
        // Step 5: Handle UPD and BankAccount type updates
        if ("UPI".equalsIgnoreCase(updatedAccount.getBankType())) {
            if (updatedAccount.getUpiId() != null) {
                existingAccount.setUpiId(updatedAccount.getUpiId());
            }
            if (updatedAccount.getUpiName() != null) {
                existingAccount.setUpiName(updatedAccount.getUpiName());
            }
            if (updatedAccount.getUpiNumber() != null) {
                existingAccount.setUpiNumber(updatedAccount.getUpiNumber());
            }
            // Reset bank details if UPI is selected
            existingAccount.setBankId(null);
            existingAccount.setBankName(null);
            existingAccount.setBankIFSC(null);
            existingAccount.setBankAccountNumber(null);
        } else if ("BankAccount".equalsIgnoreCase(updatedAccount.getBankType())) {
            if (updatedAccount.getBankId() != null) {
                existingAccount.setBankId(updatedAccount.getBankId());
            }
            if (updatedAccount.getBankName() != null) {
                existingAccount.setBankName(updatedAccount.getBankName());
            }
            if (updatedAccount.getBankIFSC() != null) {
                existingAccount.setBankIFSC(updatedAccount.getBankIFSC());
            }
            if (updatedAccount.getBankAccountNumber() != null) {
                existingAccount.setBankAccountNumber(updatedAccount.getBankAccountNumber());
            }
            // Reset UPI details if BankAccount is selected
            existingAccount.setUpiId(null);
            existingAccount.setUpiName(null);
            existingAccount.setUpiNumber(null);
        } else {
            return Response.responseFailure("Invalid bankType. Please provide 'UPI' or 'BankAccount'.");
        }
    
        // Step 6: Save the updated bank account details
        customerBankAccountRepository.save(existingAccount);
    
        // Step 7: Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bank account updated successfully.");
    
        // Return response based on the bankType
        if ("UPI".equalsIgnoreCase(existingAccount.getBankType())) {
            response.put("bankAccount", Map.of(
                    "bankType", existingAccount.getBankType(),
                    "upiId", existingAccount.getUpiId(),
                    "upiName", existingAccount.getUpiName(),
                    "upiNumber", existingAccount.getUpiNumber(),
                    "status", existingAccount.getStatus()
            ));
        } else if ("BankAccount".equalsIgnoreCase(existingAccount.getBankType())) {
            response.put("bankAccount", Map.of(
                    "bankId", existingAccount.getBankId(),
                    "bankIFSC", existingAccount.getBankIFSC(),
                    "bankType", existingAccount.getBankType(),
                    "bankName", existingAccount.getBankName(),
                    "bankAccountNumber", existingAccount.getBankAccountNumber(),
                    "status", existingAccount.getStatus()
            ));
        }
    
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> deleteCustomerBankAccount(String userId, Long bankId) {
        // Find the user by userId
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return Response.responseFailure("User with userId " + userId + " not found!");
        }
    
        // Find the bank account by bankId and ensure it belongs to the given userId
        CustomerBankAccount accountToDelete = customerBankAccountRepository.findById(bankId).orElse(null);
        if (accountToDelete == null) {
            return Response.responseFailure("No bank account found with bankId " + bankId);
        }
    
        // Ensure the account belongs to the user with the provided userId
        if (!accountToDelete.getUser().getUserId().equals(userId)) {
            return Response.responseFailure("Bank account does not belong to the user with userId " + userId);
        }
    
        // Remove the user details from the bank account before returning the response
        accountToDelete.setUser(null);  // This removes the user object from the response
    
        // Delete the account
        customerBankAccountRepository.delete(accountToDelete);
    
        // Prepare the response (only showing bank account details without user info)
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bank account deleted successfully.");
        response.put("bankAccount", accountToDelete);  // Include only the deleted bank account details without user info
    
        return ResponseEntity.ok(response);
    }
   // Service Method for Change Status
   public ResponseEntity<Map<String, Object>> changeBankAccountStatus(String userId, Long bankId) {
    // Find the user by userId
    User user = userRepository.findByUserId(userId);
    if (user == null) {
        return Response.responseFailure("User with userId " + userId + " not found!");
    }

    // Find the bank account by bankId
    CustomerBankAccount accountToChangeStatus = customerBankAccountRepository.findById(bankId).orElse(null);
    if (accountToChangeStatus == null) {
        return Response.responseFailure("No bank account found with bankId " + bankId);
    }

    // Check if the bank account belongs to the given userId
    if (!accountToChangeStatus.getUser().getUserId().equals(userId)) {
        return Response.responseFailure("Bank account with bankId " + bankId + " does not belong to userId " + userId);
    }

    // Get the current status and flip it
    String currentStatus = accountToChangeStatus.getStatus();
    String newStatus = currentStatus.equalsIgnoreCase("Active") ? "Inactive" : "Active";
    accountToChangeStatus.setStatus(newStatus);

    // Save the updated bank account
    customerBankAccountRepository.save(accountToChangeStatus);

    // Prepare the response message
    String statusMessage = currentStatus.equalsIgnoreCase("Active")
            ? "Bank account has been deactivated."
            : "Bank account has been activated.";

    // Return response with the message
    Map<String, Object> response = new HashMap<>();
    response.put("message", statusMessage);

    return ResponseEntity.ok(response);
}

    // Get All Active Admin Bank Accounts
    public ResponseEntity<Map<String, Object>> getAdminActiveBankAccounts() {
        List<BankAccount2> activeBanks = adminBankAccountRepository.findByStatusIgnoreCase("Active");
        return Response.responseSuccess("Active admin bank accounts retrieved successfully.", "activeAdminBanks", activeBanks);
    }
}