package com.scriza.in.Wealth.User.Repository;

import com.scriza.in.Wealth.User.Entity.User;  // Correct import
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);  // Correct return type
}