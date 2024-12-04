package com.scriza.in.Wealth.Admin2.CreatePlans.Entity;


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
public class Plan2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String planCode; // Unique code for the plan
    private String planName; // Name of the plan
    private double amount; // Cost of the plan
    private double dailyWithdrawalAmount; // Daily withdrawal limit
    private int duration; // Duration of the plan (in days)
}