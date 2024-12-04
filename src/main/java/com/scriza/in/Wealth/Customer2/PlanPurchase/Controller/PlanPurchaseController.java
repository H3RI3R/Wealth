package com.scriza.in.Wealth.Customer2.PlanPurchase.Controller;


import com.scriza.in.Wealth.Customer2.PlanPurchase.Service.PlanPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer/plans2")
public class PlanPurchaseController {

    @Autowired
    private PlanPurchaseService planPurchaseService;

    // Browse all available plans
    @GetMapping("/browse")
    public ResponseEntity<Map<String, Object>> browsePlans() {
        return planPurchaseService.browsePlans();
    }

    // Purchase a plan
    @PostMapping("/purchase")
    public ResponseEntity<Map<String, Object>> purchasePlan(@RequestParam String userId, @RequestParam String planCode) {
        return planPurchaseService.purchasePlan(userId, planCode);
    }
}