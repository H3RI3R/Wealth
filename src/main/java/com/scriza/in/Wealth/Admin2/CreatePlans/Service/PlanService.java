package com.scriza.in.Wealth.Admin2.CreatePlans.Service;

import com.scriza.in.Wealth.Admin2.CreatePlans.Entity.Plan2;
import com.scriza.in.Wealth.Admin2.CreatePlans.Repository.PlanRepository;
import com.scriza.in.Wealth.GlobalConfig.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;


    public ResponseEntity<Map<String, Object>> createPlan(Plan2 plan) {
        if (planRepository.findByPlanCode(plan.getPlanCode()).isPresent()) {
            return Response.responseFailure("Plan with this code already exists!");
        }
        Plan2 savedPlan = planRepository.save(plan);
        return Response.responseSuccess("Plan created successfully", "plan", savedPlan);
    }

    // Get all plans
    public ResponseEntity<Map<String, Object>> getAllPlans() {
        List<Plan2> plans = planRepository.findAll();
        return Response.responseSuccess("All plans retrieved successfully", "plans", plans);
    }

    // Get plan by plan code
    public ResponseEntity<Map<String, Object>> getPlanByPlanCode(String planCode) {
        Optional<Plan2> optionalPlan = planRepository.findByPlanCode(planCode);
        if (optionalPlan.isEmpty()) {
            return Response.responseFailure("Plan not found!");
        }
        return Response.responseSuccess("Plan retrieved successfully", "plan", optionalPlan.get());
    }

    // Update plan
    public ResponseEntity<Map<String, Object>> updatePlan(String planCode, Plan2 planDetails) {
        Optional<Plan2> optionalPlan = planRepository.findByPlanCode(planCode);
        if (optionalPlan.isEmpty()) {
            return Response.responseFailure("Plan not found!");
        }
        Plan2 plan = optionalPlan.get();
        plan.setPlanName(planDetails.getPlanName());
        plan.setAmount(planDetails.getAmount());
        plan.setDailyWithdrawalAmount(planDetails.getDailyWithdrawalAmount());
        plan.setDuration(planDetails.getDuration());
        planRepository.save(plan);
        return Response.responseSuccess("Plan updated successfully", "plan", plan);
    }

    // Delete plan
    public ResponseEntity<Map<String, Object>> deletePlan(String planCode) {
        Optional<Plan2> optionalPlan = planRepository.findByPlanCode(planCode);
        if (optionalPlan.isEmpty()) {
            return Response.responseFailure("Plan not found!");
        }
        planRepository.delete(optionalPlan.get());
        return Response.responseSuccess("Plan deleted successfully", "planCode", planCode);
    }
}