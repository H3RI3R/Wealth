package com.scriza.in.Wealth.User.User.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; 

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    private String password;

    private String role;

    @Column(nullable = true)
    private String photo;

    @Column(nullable = false)
    private String status = "active";



    private double dailyWithdrawal = 0;
    private String planName = "No Plans";
    private double totalWithdrawal = 0;
    private double totalDeposit = 0;
    private double withdrawableAmount = 0;
    private double lockedAmount = 0;
}