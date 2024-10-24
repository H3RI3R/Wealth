package com.scriza.in.Wealth.Admin.Plans.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.scriza.in.Wealth.Admin.Plans.Service.PlanService;
import com.scriza.in.Wealth.GlobalConfig.Response;
import com.scriza.in.Wealth.Admin.Plans.Entity.Plan;
import com.scriza.in.Wealth.Admin.Plans.Repository.PlanRepository;
import java.util.Map;
@RestController
@RequestMapping("/api/admin/plans")
public class PlanController {
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanService planService;

    @GetMapping("/all")
    public ResponseEntity<Map<String , Object>> getAllPlans() {
        return planService.getAllPlan();
    }
    @PostMapping("/create")
    public ResponseEntity<Map<String , Object>> createPlan(@RequestBody Plan plan) {
        Plan savedPlan = planRepository.save(plan);
        return Response.responseSuccess("Plan created successfully", "plan", savedPlan);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String , Object>> getPlanById(@PathVariable Long id) {
        return planService.getPlanById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String , Object>> updatePlan(@PathVariable Long id, @RequestBody Plan plan) {
 return planService.updatePlan(id, plan.getPlanName(), plan.getAmount(), plan.getDailyWithdrawalAmount(), plan.getDuration());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String , Object>> deletePlan(@PathVariable Long id) {
        return planService.deletePlan(id);
    }
}