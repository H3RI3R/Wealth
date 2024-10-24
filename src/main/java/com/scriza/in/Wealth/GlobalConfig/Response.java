package com.scriza.in.Wealth.GlobalConfig;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Response {

//    public static ResponseEntity<Map<String, Object>> responseSuccess(String message) {
//        Map<String, Object> successResponse = new HashMap<>();
//        successResponse.put("message", message);
//        successResponse.put("status", "success");
//        return ResponseEntity.ok(successResponse);
//    }

    public static ResponseEntity<Map<String, Object>> responseSuccess(String msg, String inputType, Object inputdata) {
        Map<String, Object> successResponse = new HashMap<>();


        successResponse.put(inputType, inputdata);
        successResponse.put("status", "success");
        successResponse.put("message", msg);
        return ResponseEntity.ok(successResponse);
    }


    public static ResponseEntity<Map<String, Object>> responseFailure(String message) {
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("message", message);
        successResponse.put("status", "failure");
        return ResponseEntity.ok(successResponse);
    }

}
