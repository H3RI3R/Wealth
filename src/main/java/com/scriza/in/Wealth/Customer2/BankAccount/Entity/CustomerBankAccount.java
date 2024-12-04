package com.scriza.in.Wealth.Customer2.BankAccount.Entity;


import com.scriza.in.Wealth.User.User.Entity.User;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "customer_bank_accounts")
public class CustomerBankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bankType; // UPI or BankAccount
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // BankAccount fields
    @Column(unique = true) // Ensure uniqueness for bankId
    private Long bankId;  // No @GeneratedValue here

    private String bankName;
    private String bankIFSC;
    private String bankAccountNumber;

    // UPI fields
    private String upiId;
    private String upiName;
    private String upiNumber;

    @Column(nullable = false)
    private String status = "Active"; // Active by default
}