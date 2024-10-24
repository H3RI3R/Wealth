package com.scriza.in.Wealth.BankDetails.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scriza.in.Wealth.BankDetails.Entity.Bank;

public interface BankRepository extends JpaRepository<Bank,Long>{

    List<Bank> findByUserUserId(String userId);
} 
