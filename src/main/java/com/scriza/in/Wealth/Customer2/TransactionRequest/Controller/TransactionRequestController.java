package com.scriza.in.Wealth.Customer2.TransactionRequest.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.scriza.in.Wealth.Customer2.TransactionRequest.DTO.TransactionRequestDTO;
import com.scriza.in.Wealth.Customer2.TransactionRequest.Entity.TransactionRequest;
import com.scriza.in.Wealth.Customer2.TransactionRequest.Service.TransactionRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/transaction-requests")
public class TransactionRequestController {

    @Autowired
    private TransactionRequestService transactionRequestService;

    // Endpoint to create a transaction request
    @PostMapping("/create")
    public TransactionRequest createTransactionRequest(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        return transactionRequestService.createTransactionRequest(transactionRequestDTO);
    }

    // Endpoint to get all transaction requests for a user
    @GetMapping("/user/{userId}")
    public List<TransactionRequest> getTransactionRequests(@PathVariable String userId) {
        return transactionRequestService.getTransactionRequests(userId);
    }
}