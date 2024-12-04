package com.scriza.in.Wealth.Admin2.BankAccount.Repository;


import com.scriza.in.Wealth.Admin2.BankAccount.Entity.BankAccount2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount2, Long> {
    List<BankAccount2> findByStatusIgnoreCase(String status);
}