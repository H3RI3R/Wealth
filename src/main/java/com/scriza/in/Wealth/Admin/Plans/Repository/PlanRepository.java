package com.scriza.in.Wealth.Admin.Plans.Repository;


import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scriza.in.Wealth.Admin.Plans.Entity.Plan;
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByOrderByIdAsc();
    Plan findByPlanCode(String planCode);

}