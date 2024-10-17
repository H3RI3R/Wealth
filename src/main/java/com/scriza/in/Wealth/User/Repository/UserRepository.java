package com.scriza.in.Wealth.User.Repository;

import com.scriza.in.Wealth.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);  
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByUserId(String userId);
}