package com.scriza.in.Wealth.GlobalConfig;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Response {

    // Success response with message, input type, and data
    public static ResponseEntity<Map<String, Object>> responseSuccess(String msg, String inputType, Object inputdata) {
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put(inputType, inputdata);
        successResponse.put("status", "success");
        successResponse.put("message", msg);
        return ResponseEntity.ok(successResponse); // HTTP 200 OK
    }

    // Failure response with a message
    public static ResponseEntity<Map<String, Object>> responseFailure(String message) {
        Map<String, Object> failureResponse = new HashMap<>();
        failureResponse.put("message", message);
        failureResponse.put("status", "failure");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failureResponse); // HTTP 400 Bad Request
    }

    // Failure response with customizable HTTP status (e.g., 404, 500, etc.)
    public static ResponseEntity<Map<String, Object>> responseFailure(String message, HttpStatus status) {
        Map<String, Object> failureResponse = new HashMap<>();
        failureResponse.put("message", message);
        failureResponse.put("status", "failure");
        return ResponseEntity.status(status).body(failureResponse);
    }
}