package com.scriza.in.Wealth.Admin2.CreatePlans.Controller;


import com.scriza.in.Wealth.Admin2.CreatePlans.Entity.Plan2;
import com.scriza.in.Wealth.Admin2.CreatePlans.Service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/plans2")
public class PlanController {

    @Autowired
    private PlanService planService;

    // Create a new plan
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPlan(@RequestBody Plan2 plan) {
        return planService.createPlan(plan);
    }

    // Get all plans
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPlans() {
        return planService.getAllPlans();
    }

    // Get plan by plan code
    @GetMapping("/code/{planCode}")
    public ResponseEntity<Map<String, Object>> getPlanByPlanCode(@PathVariable String planCode) {
        return planService.getPlanByPlanCode(planCode);
    }

    // Update plan
    @PutMapping("/update/{planCode}")
    public ResponseEntity<Map<String, Object>> updatePlan(@PathVariable String planCode, @RequestBody Plan2 planDetails) {
        return planService.updatePlan(planCode, planDetails);
    }

    // Delete pla
    @DeleteMapping("/delete/{planCode}")
    public ResponseEntity<Map<String, Object>> deletePlan(@PathVariable String planCode) {
        return planService.deletePlan(planCode);
    }
}