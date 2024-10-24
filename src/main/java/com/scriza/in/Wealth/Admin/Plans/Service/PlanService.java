package com.scriza.in.Wealth.Admin.Plans.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.scriza.in.Wealth.Admin.Plans.Entity.Plan;
import com.scriza.in.Wealth.Admin.Plans.Repository.PlanRepository;
import com.scriza.in.Wealth.GlobalConfig.Response; 
import java.util.Map;

import java.util.Optional;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public ResponseEntity<Map<String, Object>> updatePlan(Long planId, String planName, double amount, double dailyWithdrawalAmount, int duration) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        if (optionalPlan.isPresent()) {
            Plan plan = optionalPlan.get();
            plan.setPlanName(planName);
            plan.setAmount(amount);
            plan.setDailyWithdrawalAmount(dailyWithdrawalAmount);
            plan.setDuration(duration);
            planRepository.save(plan);
            return Response.responseSuccess("Plan updated successfully", "plan", plan);
        } else {
            return Response.responseFailure("Plan not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Map<String, Object>> deletePlan(Long planId) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        if (optionalPlan.isPresent()) {
            planRepository.delete(optionalPlan.get());
            return Response.responseSuccess("Plan deleted successfully", "planId", planId);
        } else {
            return Response.responseFailure("Plan not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Map<String, Object>> getPlanById(Long planId) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        return optionalPlan.map(plan -> Response.responseSuccess("Plan retrieved successfully", "plan", plan))
                           .orElse(Response.responseFailure("Plan not found", HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Map<String, Object>> getAllPlan() {
        List<Plan> plans = planRepository.findAll(); 
        if (plans.isEmpty()) {
            return Response.responseFailure("No plans found."); 
        }
        return Response.responseSuccess("Plans retrieved successfully", "plans", plans);
    }
    
}