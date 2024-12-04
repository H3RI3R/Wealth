package com.scriza.in.Wealth.Admin2.TransactionApproval.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.scriza.in.Wealth.Customer2.TransactionRequest.Entity.TransactionRequest;

import java.util.List;
import java.util.Optional;

public interface TransactionRequestRepositoryAdmin extends JpaRepository<TransactionRequest, Long> {
    List<TransactionRequest> findByUserId(String userId);  
    Optional<TransactionRequest> findByRequestId(String requestId);  
}