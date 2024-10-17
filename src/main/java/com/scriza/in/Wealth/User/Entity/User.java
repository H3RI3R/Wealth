package com.scriza.in.Wealth.User.Entity;
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

    @Column(name = "email", nullable = false) 
    private String email;

    private String password;
    private String role;
    @Column(nullable = true)
    private String photo; 


}