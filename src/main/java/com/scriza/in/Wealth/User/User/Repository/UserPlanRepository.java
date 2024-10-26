package com.scriza.in.Wealth.User.User.Repository;
import com.scriza.in.Wealth.User.User.Entity.User;
import com.scriza.in.Wealth.User.User.Entity.UserPlan;
import com.scriza.in.Wealth.Admin.Plans.Entity.Plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
    List<UserPlan> findByUser(User user);
    UserPlan findByUserAndPlan(User user, Plan plan);
}