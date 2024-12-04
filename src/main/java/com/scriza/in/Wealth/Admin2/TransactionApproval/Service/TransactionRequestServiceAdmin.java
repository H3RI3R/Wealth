package com.scriza.in.Wealth.Admin2.TransactionApproval.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scriza.in.Wealth.Customer2.TransactionRequest.Entity.TransactionRequest;
import com.scriza.in.Wealth.Customer2.TransactionRequest.Repository.TransactionRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionRequestServiceAdmin {

    @Autowired
    private TransactionRequestRepository transactionRequestRepository;

    // Method to get all transaction requests (for the admin)
    public List<TransactionRequest> getAllTransactionRequests() {
        return transactionRequestRepository.findAll();  // Fetch all transaction requests
    }

    // Method to update the status of a specific transaction request (Accept/Reject)
    public TransactionRequest updateTransactionRequestStatus(String requestId, String status) {
        Optional<TransactionRequest> optionalRequest = transactionRequestRepository.findByRequestId(requestId);

        if (optionalRequest.isPresent()) {
            TransactionRequest transactionRequest = optionalRequest.get();
            transactionRequest.setStatus(status);  // Update the status
            return transactionRequestRepository.save(transactionRequest);  // Save updated request
        } else {
            return null;  // Return null if request ID is not found
        }
    }
}