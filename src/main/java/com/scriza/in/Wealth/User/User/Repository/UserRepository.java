package com.scriza.in.Wealth.User.User.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scriza.in.Wealth.User.User.Entity.User;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);  
    boolean existsByEmail(String email);
    User findByEmail(String email);

    User findByUserId(String userId);


}