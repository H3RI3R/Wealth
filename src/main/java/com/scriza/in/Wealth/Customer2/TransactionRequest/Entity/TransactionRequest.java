package com.scriza.in.Wealth.Customer2.TransactionRequest.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Setter
@Getter
@Entity
@Data
public class TransactionRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String requestId;
    private String userId;
    private String userName;
    private LocalDateTime requestDate;
    private Double requestedAmount;
    private String status = "Pending";  // Default status
    private String paymentMethod;
    private String accountDetails;
}