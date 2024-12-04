package com.scriza.in.Wealth.Admin2.BankAccount.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BankAccount2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankType; // UPI / BankAccount

    // BankAccount fields
    private String bankId; // Unique Bank ID
    private String bankName; // Bank Name
    private String bankIFSC; // IFSC Code
    private String bankAccountNumber; // Account Number

    // UPI fields
    private String upiId; // UPI ID
    private String upiName; // UPI Name
    private String upiNumber; // UPI Number
    
    private String status; // Active or Inactive
}