package com.scriza.in.Wealth.User.User.Service;

import com.scriza.in.Wealth.Admin.Plans.Entity.Plan;
import com.scriza.in.Wealth.Admin.Plans.Repository.PlanRepository;

import com.scriza.in.Wealth.GlobalConfig.Response;
import com.scriza.in.Wealth.User.User.Entity.Transaction;
import com.scriza.in.Wealth.User.User.Entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.scriza.in.Wealth.User.User.Entity.UserPlan;
import com.scriza.in.Wealth.User.User.Repository.TransactionRepository;
import com.scriza.in.Wealth.User.User.Repository.UserPlanRepository;
import com.scriza.in.Wealth.User.User.Repository.UserRepository;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserPlanRepository userPlanRepository;

    @Autowired
    private TransactionRepository transactionRepository;




    private final String uploadDir = "/Users/ritiksoni/Downloads/Wealth/src/main/java/com/scriza/in/Wealth/Images";

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        return userRepository.save(user); 
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    public String generateUniqueUserId() {
        String userId;
        Random random = new Random();
        do {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder charPart = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                charPart.append(characters.charAt(random.nextInt(characters.length())));
            }
            int numberPart = random.nextInt(900) + 100; 
            userId = charPart.toString() + numberPart;
        } while (userRepository.findByUserId(userId) != null);
        return userId;
    }

    public String savePhoto(MultipartFile photo) {
        // Implement file saving logic and return the file URL or path
        return "/path/to/photo";
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User dbUser = getUserByEmail(email);
        if (dbUser != null) {
            return new CustomUserDetails(dbUser);
        } else {
            throw new AccessDeniedException(email);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }
    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
    public User modifyUser(String userId, User userDetails) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return null; 
        }

       
        if (userDetails.getUserName() != null) {
            user.setUserName(userDetails.getUserName());
        }
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        
        return userRepository.save(user); 
    }
    public User updateProfilePicture(String userId, MultipartFile file) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return null;
        }


        Path filePath = Paths.get(uploadDir, userId + "_profile_picture.jpg");

        try {

            file.transferTo(filePath.toFile()); 
            user.setPhoto(filePath.toString()); 
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return userRepository.save(user);
    }

 public ResponseEntity<Map<String, Object>> getAvailablePlans() {
    List<Plan> availablePlans = planRepository.findAll();
    List<Map<String, Object>> plansResponse = availablePlans.stream()
            .map(plan -> {
                Map<String, Object> planMap = new HashMap<>();
                planMap.put("planId", plan.getPlanCode());
                planMap.put("planName", plan.getPlanName());
                planMap.put("amount", plan.getAmount());
                planMap.put("dailyWithdrawalAmount", plan.getDailyWithdrawalAmount());
                planMap.put("duration", plan.getDuration());
                return planMap;
            }).collect(Collectors.toList());
    return Response.responseSuccess("Available plans retrieved successfully", "plans", plansResponse);
}

public ResponseEntity<Map<String, Object>> purchasePlan(String planId, String userId) {
    Plan plan = planRepository.findByPlanCode(planId);
    if (plan == null) {
        return Response.responseFailure("Plan not found");
    }

    User user = userRepository.findByUserId(userId); 
    if (user == null) {
        return Response.responseFailure("User not found");
    }

    UserPlan userPlan = new UserPlan();
    userPlan.setUser(user);
    userPlan.setPlan(plan);
    userPlan.setStartDate(LocalDate.now());
    userPlan.setEndDate(LocalDate.now().plusDays(plan.getDuration()));
    userPlan.setStatus("active");
    userPlan.setUserCode(userId); 
    userPlanRepository.save(userPlan);

    Transaction transaction = new Transaction();
    transaction.setUser(user);
    transaction.setPlan(plan);
    transaction.setAmount(plan.getAmount());
    transaction.setTimestamp(LocalDateTime.now());
    transaction.setStatus("Success");
    transaction.setUserCode(userId); 
    transactionRepository.save(transaction);

    updateUserWithPlan(user, plan);

    Map<String, Object> walletStatus = new HashMap<>();
    walletStatus.put("lockedAmount", plan.getAmount());
    walletStatus.put("dailyWithdrawalAdded", plan.getDailyWithdrawalAmount());

    return Response.responseSuccess("Plan purchased successfully", "walletStatus", walletStatus);
}

private void updateUserWithPlan(User user, Plan plan) {

    List<String> existingPlans = new ArrayList<>(Arrays.asList(user.getPlanName().split(",")));
    existingPlans.add(plan.getPlanName()); 
    user.setPlanName(String.join(",", existingPlans)); 
    
    user.setTotalDeposit(user.getTotalDeposit() + plan.getAmount());


    user.setWithdrawableAmount(user.getWithdrawableAmount() + plan.getDailyWithdrawalAmount());

    
    userRepository.save(user);
}


public ResponseEntity<Map<String, Object>> getUserPlans(String userId) {
    User user = userRepository.findByUserId(userId);
    if (user == null) {
        return Response.responseFailure("User not found");
    }

    List<UserPlan> userPlans = userPlanRepository.findByUser(user);
    List<Map<String, Object>> plansResponse = userPlans.stream()
            .map(userPlan -> {
                Map<String, Object> planMap = new HashMap<>();
                planMap.put("planId", userPlan.getPlan().getPlanCode());
                planMap.put("planName", userPlan.getPlan().getPlanName());
                planMap.put("amount", userPlan.getPlan().getAmount());
                planMap.put("dailyWithdrawalAmount", userPlan.getPlan().getDailyWithdrawalAmount());
                planMap.put("duration", userPlan.getPlan().getDuration());
                planMap.put("startDate", userPlan.getStartDate());
                planMap.put("endDate", userPlan.getEndDate());
                planMap.put("status", userPlan.getStatus());
                return planMap;
            }).collect(Collectors.toList());
    return Response.responseSuccess("User plans retrieved successfully", "plans", plansResponse);
}

public ResponseEntity<Map<String, Object>> getDailyEarnings(String userId) {
    User user = userRepository.findByUserId(userId);
    if (user == null) {
        return Response.responseFailure("User not found");
    }

    double dailyEarnings = calculateDailyEarnings(user);
    double totalWithdrawableAmount = calculateTotalWithdrawableAmount(user);

    Map<String, Object> earningsResponse = new HashMap<>();
    earningsResponse.put("dailyEarnings", dailyEarnings);
    earningsResponse.put("totalWithdrawableAmount", totalWithdrawableAmount);

    return Response.responseSuccess("Daily earnings retrieved successfully", "earnings", earningsResponse);
}

private double calculateDailyEarnings(User user) {

    List<UserPlan> userPlans = userPlanRepository.findByUser(user); 
    double dailyEarnings = 0;
    for (UserPlan userPlan : userPlans) {
        if ("active".equalsIgnoreCase(userPlan.getStatus())) {

            dailyEarnings += userPlan.getPlan().getDailyWithdrawalAmount();
        }
    }

    return dailyEarnings; 
}

private double calculateTotalWithdrawableAmount(User user) {

    return user.getWithdrawableAmount(); 
}


public User findUserByEmail(String email) {
    return userRepository.findByEmail(email); // Assuming there's a UserRepository with a method findByEmail
}

}