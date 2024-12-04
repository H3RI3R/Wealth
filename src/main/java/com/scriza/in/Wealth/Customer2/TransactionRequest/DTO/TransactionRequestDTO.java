package com.scriza.in.Wealth.Customer2.TransactionRequest.DTO;
import lombok.Data;

@Data
public class TransactionRequestDTO {
    private String userId;
    private String userName;
    private Double requestedAmount;
    private String paymentMethod;
    private String accountDetails;
}