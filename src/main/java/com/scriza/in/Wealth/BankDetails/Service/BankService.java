package com.scriza.in.Wealth.BankDetails.Service;

import java.util.List;
import java.util.Optional; // Correct import


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.scriza.in.Wealth.BankDetails.Entity.Bank;
import com.scriza.in.Wealth.BankDetails.Repository.BankRepository;
import com.scriza.in.Wealth.GlobalConfig.Response;

@Service
public class BankService {

    @Autowired
    private BankRepository paymentAccountRepository;


    public ResponseEntity<?> saveOrUpdatePaymentAccount(Bank paymentAccount) {
        paymentAccountRepository.save(paymentAccount);
        return Response.responseSuccess("Payment account saved successfully", "paymentAccount", paymentAccount);
    }


    public ResponseEntity<?> getAllPaymentAccounts() {
        return Response.responseSuccess("List of payment accounts", "paymentAccounts", paymentAccountRepository.findAll());
    }


    public ResponseEntity<?> getPaymentAccountById(Long id) {
        Optional<Bank> paymentAccount = paymentAccountRepository.findById(id);
        if (paymentAccount.isPresent()) {
            return Response.responseSuccess("Payment account found", "paymentAccount", paymentAccount.get());
        }
        return Response.responseFailure("Payment account not found");
    }


    public ResponseEntity<?> deletePaymentAccount(Long id) {
        Optional<Bank> paymentAccount = paymentAccountRepository.findById(id);
        if (paymentAccount.isPresent()) {
            paymentAccountRepository.delete(paymentAccount.get());
            return Response.responseSuccess("Payment account deleted successfully", "paymentAccountId", id);
        }
        return Response.responseFailure("Payment account not found");
    }


    public ResponseEntity<?> updatePaymentAccount(Long id, Bank updatedPaymentAccount) {
        Optional<Bank> bankOptional = paymentAccountRepository.findById(id);
        if (bankOptional.isPresent()) {
            Bank existingBank = bankOptional.get();

            existingBank.setPaymentType(updatedPaymentAccount.getPaymentType());
            existingBank.setBankName(updatedPaymentAccount.getBankName());
            existingBank.setBankAccountNumber(updatedPaymentAccount.getBankAccountNumber());
            existingBank.setBankIFSC(updatedPaymentAccount.getBankIFSC());
            existingBank.setAccountHolderName(updatedPaymentAccount.getAccountHolderName());
            existingBank.setUpiAccountId(updatedPaymentAccount.getUpiAccountId());
            existingBank.setUpiProvider(updatedPaymentAccount.getUpiProvider());
            existingBank.setPhoneNumber(updatedPaymentAccount.getPhoneNumber());
            existingBank.setUpiAccountHolderName(updatedPaymentAccount.getUpiAccountHolderName());

            paymentAccountRepository.save(existingBank);
            return Response.responseSuccess("Bank details updated successfully", "bankId", id);
        } else {
            return Response.responseFailure("Bank account not found");
        }
    }
     public ResponseEntity<?> getAllPaymentAccountsByUserId(String userId) {
        List<Bank> bankAccounts = paymentAccountRepository.findByUserUserId(userId);
        if (!bankAccounts.isEmpty()) {
            return Response.responseSuccess("List of bank accounts", "bankAccounts", bankAccounts);
        }
        return Response.responseFailure("No bank accounts found for the user");
    }


    public List<Bank> getBankDetailsByUserId(String userId) {
        return paymentAccountRepository.findByUserUserId(userId);
    }

    public ResponseEntity<?> updateBankDetails(String userId, Long bankId, Bank updatedBankDetails) {

        Optional<Bank> existingBankOptional = paymentAccountRepository.findById(bankId);
        
        if (!existingBankOptional.isPresent()) {
            return Response.responseFailure("Bank details not found for bank ID: " + bankId);
        }
    
        Bank existingBank = existingBankOptional.get();
        

        System.out.println("Existing User ID: " + existingBank.getUser().getUserId());
        System.out.println("Provided User ID: " + userId);
    
        if (!existingBank.getUser().getUserId().equals(userId)) {
            return Response.responseFailure("Bank details do not belong to the user ID: " + userId);
        }
    

        existingBank.setPaymentType(updatedBankDetails.getPaymentType());
        existingBank.setBankName(updatedBankDetails.getBankName());
        existingBank.setBankAccountNumber(updatedBankDetails.getBankAccountNumber());
        existingBank.setBankIFSC(updatedBankDetails.getBankIFSC());
        existingBank.setAccountHolderName(updatedBankDetails.getAccountHolderName());
        existingBank.setUpiAccountId(updatedBankDetails.getUpiAccountId());
        existingBank.setUpiProvider(updatedBankDetails.getUpiProvider());
        existingBank.setPhoneNumber(updatedBankDetails.getPhoneNumber());
        existingBank.setUpiAccountHolderName(updatedBankDetails.getUpiAccountHolderName());
    
        paymentAccountRepository.save(existingBank);
    
        return Response.responseSuccess("Bank details updated successfully", "bankId", bankId);
    }
   

}