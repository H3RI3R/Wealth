package com.scriza.in.Wealth.Admin2.CreatePlans.Repository;


import com.scriza.in.Wealth.Admin2.CreatePlans.Entity.Plan2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan2, Long> {

    Optional<Plan2> findByPlanCode(String planCode);

}