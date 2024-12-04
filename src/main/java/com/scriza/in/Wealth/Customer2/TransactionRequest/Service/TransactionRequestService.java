package com.scriza.in.Wealth.Customer2.TransactionRequest.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scriza.in.Wealth.Customer2.TransactionRequest.DTO.TransactionRequestDTO;
import com.scriza.in.Wealth.Customer2.TransactionRequest.Entity.TransactionRequest;
import com.scriza.in.Wealth.Customer2.TransactionRequest.Repository.TransactionRequestRepository;
import com.scriza.in.Wealth.User.User.Entity.User;
import com.scriza.in.Wealth.User.User.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TransactionRequestService {

    @Autowired
    private TransactionRequestRepository transactionRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to create a new transaction request
    public TransactionRequest createTransactionRequest(TransactionRequestDTO transactionRequestDTO) {
        // Generate a unique request ID (e.g., REQ1234)
        String requestId = generateRequestId();
        
        // Fetch user data (userId, userName) from the user entity
        User user = userRepository.findByUserId(transactionRequestDTO.getUserId());
        
        // Create a new transaction request
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setRequestId(requestId);
        transactionRequest.setUserId(user.getUserId());
        transactionRequest.setUserName(user.getUserName());
        transactionRequest.setRequestDate(LocalDateTime.now());
        transactionRequest.setRequestedAmount(transactionRequestDTO.getRequestedAmount());
        transactionRequest.setPaymentMethod(transactionRequestDTO.getPaymentMethod());
        transactionRequest.setAccountDetails(transactionRequestDTO.getAccountDetails());
        
        // Save the request to the database
        return transactionRequestRepository.save(transactionRequest);
    }

    // Method to generate a unique request ID
    private String generateRequestId() {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);  // Random number between 1000 and 9999
        return "REQ" + randomNumber;
    }

    // Method to get all transaction requests for a user
    public List<TransactionRequest> getTransactionRequests(String userId) {
        return transactionRequestRepository.findByUserId(userId);
    }
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