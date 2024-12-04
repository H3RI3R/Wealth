package com.scriza.in.Wealth.Customer2.BankAccount.Repository;


import com.scriza.in.Wealth.Customer2.BankAccount.Entity.CustomerBankAccount;
import com.scriza.in.Wealth.User.User.Entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerBankAccountRepository extends JpaRepository<CustomerBankAccount, Long> {
    List<CustomerBankAccount> findByUser(User user);
    CustomerBankAccount findByUserAndBankId(User user, Long bankId);
}