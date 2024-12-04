package com.scriza.in.Wealth.Admin2.TransactionApproval.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scriza.in.Wealth.Customer2.TransactionRequest.Entity.TransactionRequest;
import com.scriza.in.Wealth.Customer2.TransactionRequest.Service.TransactionRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/transaction-requests")
public class TransactionRequestAdminController {

    @Autowired
    private TransactionRequestService transactionRequestService;

    // Endpoint to get all transaction requests for all users
    @GetMapping("/all")
    public List<TransactionRequest> getAllTransactionRequests() {
        return transactionRequestService.getAllTransactionRequests();
    }

    // Endpoint to update the status of a specific transaction request (Accept/Reject)
    @PutMapping("/update/{requestId}")
    public ResponseEntity<TransactionRequest> updateTransactionRequestStatus(
            @PathVariable String requestId, 
            @RequestParam String status) {
        // Validate the status (should be "Accepted" or "Rejected")
        if (!status.equalsIgnoreCase("Accepted") && !status.equalsIgnoreCase("Rejected")) {
            return ResponseEntity.badRequest().body(null);  // Bad Request if status is invalid
        }

        TransactionRequest updatedRequest = transactionRequestService.updateTransactionRequestStatus(requestId, status);
        if (updatedRequest != null) {
            return ResponseEntity.ok(updatedRequest);  // Return updated request if successful
        } else {
            return ResponseEntity.notFound().build();  // Return Not Found if request ID doesn't exist
        }
    }
}