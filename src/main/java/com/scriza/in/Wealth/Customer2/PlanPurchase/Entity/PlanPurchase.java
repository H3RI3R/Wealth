package com.scriza.in.Wealth.Customer2.PlanPurchase.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PlanPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // User ID purchasing the plan
    private String purchaseId; // Unique purchase ID
    private String planCode; // Plan Code from Plan2
    private String planName; // Plan Name from Plan2
    private double amount; // Plan amount from Plan2
    private double dailyWithdrawalAmount; // Daily withdrawal amount from Plan2
    private int duration; // Plan duration from Plan2
    private LocalDateTime purchaseDateTime; // Purchase date and time
}