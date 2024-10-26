package com.scriza.in.Wealth.Admin.Plans.Service;

import com.scriza.in.Wealth.Admin.Plans.Entity.Plan;
import com.scriza.in.Wealth.Admin.Plans.Repository.PlanRepository;
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


    public ResponseEntity<Map<String, Object>> getAllPlan() {
        List<Plan> plans = planRepository.findAll();
        return Response.responseSuccess("Plans retrieved successfully", "plans", plans);
    }


    public ResponseEntity<Map<String, Object>> createPlan(Plan plan) {
        Long planCount = planRepository.count(); 
        String newPlanCode = "plan" + (planCount + 1); // This will be the custom planCode
        plan.setPlanCode(newPlanCode);
        Plan savedPlan = planRepository.save(plan); 
        return Response.responseSuccess("Plan created successfully", "plan", savedPlan);
    }


    public ResponseEntity<Map<String, Object>> getPlanById(Long id) {
        Optional<Plan> plan = planRepository.findById(id);
        if (plan.isPresent()) {
            return Response.responseSuccess("Plan retrieved successfully", "plan", plan.get());
        } else {
            return Response.responseFailure("Plan not found");
        }
    }
    public ResponseEntity<Map<String, Object>> getPlanByPlanCode(String planCode) {
        Plan plan = planRepository.findByPlanCode(planCode);
        if (plan != null) {
            return Response.responseSuccess("Plan retrieved successfully", "plan", plan);
        } else {
            return Response.responseFailure("Plan not found");
        }
    }


    public ResponseEntity<Map<String, Object>> updatePlan(String planCode, Plan planDetails) {
        Plan existingPlan = planRepository.findByPlanCode(planCode);
        if (existingPlan != null) {
            existingPlan.setPlanName(planDetails.getPlanName());
            existingPlan.setAmount(planDetails.getAmount());
            existingPlan.setDailyWithdrawalAmount(planDetails.getDailyWithdrawalAmount());
            existingPlan.setDuration(planDetails.getDuration());
            planRepository.save(existingPlan);
            return Response.responseSuccess("Plan updated successfully", "plan", existingPlan);
        } else {
            return Response.responseFailure("Plan not found");
        }
    }
    
    public ResponseEntity<Map<String, Object>> deletePlan(String planCode) {
        Plan existingPlan = planRepository.findByPlanCode(planCode);
        if (existingPlan != null) {
            planRepository.delete(existingPlan);
            readjustPlanCodes(); 
            return Response.responseSuccess("Plan deleted successfully", "data", null); 
        } else {
            return Response.responseFailure("Plan not found");
        }
    }


    private void readjustPlanCodes() {
        List<Plan> plans = planRepository.findAllByOrderByIdAsc();
        for (int i = 0; i < plans.size(); i++) {
            Plan plan = plans.get(i);
            String newPlanCode = "plan" + (i + 1); 
            plan.setPlanCode(newPlanCode);
            planRepository.save(plan);
        }
    }
}