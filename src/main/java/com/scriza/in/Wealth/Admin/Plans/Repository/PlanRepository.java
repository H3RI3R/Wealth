package com.scriza.in.Wealth.Admin.Plans.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scriza.in.Wealth.Admin.Plans.Entity.Plan;
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

}