package com.scriza.in.Wealth.Admin.Plans.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.scriza.in.Wealth.Admin.Plans.Service.PlanService;

import com.scriza.in.Wealth.Admin.Plans.Entity.Plan;

import java.util.Map;
@RestController
@RequestMapping("/api/admin/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPlans() {
        return planService.getAllPlan();
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPlan(@RequestBody Plan plan) {
        return planService.createPlan(plan);
    }

    @GetMapping("/Id/{planCode}")
    public ResponseEntity<Map<String, Object>> getPlanByPlanCode(@PathVariable String planCode) {
        return planService.getPlanByPlanCode(planCode);
    }

    @PutMapping("/update/{planCode}")
    public ResponseEntity<Map<String, Object>> updatePlan(
            @PathVariable String planCode,
            @RequestBody Plan planDetails) {
        return planService.updatePlan(planCode, planDetails);
    }

    @DeleteMapping("/delete/{planCode}")
    public ResponseEntity<Map<String, Object>> deletePlan(@PathVariable String planCode) {
        return planService.deletePlan(planCode);
    }
}