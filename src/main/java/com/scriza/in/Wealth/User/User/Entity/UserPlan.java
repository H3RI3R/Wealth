package com.scriza.in.Wealth.User.User.Entity;

import com.scriza.in.Wealth.Admin.Plans.Entity.Plan;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "user_plans")
public class UserPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;
    
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    private String userCode; 
}