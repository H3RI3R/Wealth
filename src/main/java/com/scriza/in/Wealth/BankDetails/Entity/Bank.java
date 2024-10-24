package com.scriza.in.Wealth.BankDetails.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scriza.in.Wealth.User.User.Entity.User;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentType; 

    private String bankName;
    private String bankAccountNumber;
    private String bankIFSC;
    private String accountHolderName;

    private String upiAccountId;
    private String upiProvider;
    private String phoneNumber;
    private String upiAccountHolderName;


  @ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
@JsonIgnore 
private User user;
}