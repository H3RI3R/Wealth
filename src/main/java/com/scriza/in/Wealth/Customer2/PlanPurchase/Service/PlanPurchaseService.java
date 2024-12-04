package com.scriza.in.Wealth.Customer2.PlanPurchase.Service;


import com.scriza.in.Wealth.Admin2.CreatePlans.Entity.Plan2;
import com.scriza.in.Wealth.Admin2.CreatePlans.Repository.PlanRepository;
import com.scriza.in.Wealth.Customer2.PlanPurchase.Entity.PlanPurchase;
import com.scriza.in.Wealth.Customer2.PlanPurchase.Repository.PlanPurchaseRepository;
import com.scriza.in.Wealth.GlobalConfig.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlanPurchaseService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanPurchaseRepository planPurchaseRepository;

    // Browse all available plans
    public ResponseEntity<Map<String, Object>> browsePlans() {
        List<Plan2> plans = planRepository.findAll();
        return Response.responseSuccess("Plans retrieved successfully", "plans", plans);
    }

    // Purchase a plan
    public ResponseEntity<Map<String, Object>> purchasePlan(String userId, String planCode) {
        Optional<Plan2> optionalPlan = planRepository.findByPlanCode(planCode);
        if (optionalPlan.isEmpty()) {
            return Response.responseFailure("Plan not found!");
        }

        Plan2 plan = optionalPlan.get();
        PlanPurchase planPurchase = new PlanPurchase();
        planPurchase.setUserId(userId);
        planPurchase.setPurchaseId(UUID.randomUUID().toString());
        planPurchase.setPlanCode(plan.getPlanCode());
        planPurchase.setPlanName(plan.getPlanName());
        planPurchase.setAmount(plan.getAmount());
        planPurchase.setDailyWithdrawalAmount(plan.getDailyWithdrawalAmount());
        planPurchase.setDuration(plan.getDuration());
        planPurchase.setPurchaseDateTime(LocalDateTime.now());

        PlanPurchase savedPurchase = planPurchaseRepository.save(planPurchase);

        return Response.responseSuccess("Plan purchased successfully", "purchase", savedPurchase);
    }
}