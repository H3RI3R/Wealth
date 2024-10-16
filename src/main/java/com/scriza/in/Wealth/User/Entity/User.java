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

    @Column(nullable = false)  // Marked as NOT NULL in the database
    private String userName;

    @Column(name = "email", nullable = false)  // Ensure this maps correctly
    private String email;

    private String password;
    private String role;

    // Getters and Setters
}