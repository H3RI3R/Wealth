package com.scriza.in.Wealth.User.User.Repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scriza.in.Wealth.User.User.Entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}