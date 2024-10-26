package com.scriza.in.Wealth.BankDetails.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BankDTO {
    private Long id;
    private String paymentType; 
    private String bankName;
    private String bankAccountNumber;
    private String bankIFSC;
    private String accountHolderName;
    private String upiAccountId;
    private String upiProvider;
    private String phoneNumber;
    private String upiAccountHolderName;

    // Constructor, Getters, and Setters
}