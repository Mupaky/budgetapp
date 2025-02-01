package com.supplyboost.budgetapp.user.service;

import com.supplyboost.budgetapp.budget.service.BudgetService;
import com.supplyboost.budgetapp.exception.DomainException;
import com.supplyboost.budgetapp.subscription.service.SubscriptionService;
import com.supplyboost.budgetapp.user.model.User;
import com.supplyboost.budgetapp.user.model.UserRole;
import com.supplyboost.budgetapp.user.repository.UserRepository;
import com.supplyboost.budgetapp.web.dto.LoginRequest;
import com.supplyboost.budgetapp.web.dto.RegisterRequest;
import com.supplyboost.budgetapp.web.dto.UserEditRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionService subscriptionService;
    private final BudgetService budgetService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SubscriptionService subscriptionService, BudgetService budgetService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.subscriptionService = subscriptionService;
        this.budgetService = budgetService;
    }


    public User login(LoginRequest loginRequest){
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if(optionalUser.isEmpty()){
            throw new DomainException("Username or password are incorrect");
        }

        User user = optionalUser.get();

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new DomainException("Username or password are incorrect");
        }

        return user;
    }

    @Transactional
    public User register(RegisterRequest registerRequest){

        Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());

        if(userOptional.isPresent()){
            throw new DomainException("Username [%s] already exist.".formatted(registerRequest.getUsername()));
        }

        User user = userRepository.save(initializeUser(registerRequest));

        subscriptionService.createDefaultSubscription(user);
        budgetService.createNewBudget(user);

        log.info("Successfully create new user account [%s] and id [%s]".formatted(user.getUsername(), user.getId()));

        return user;
    }

    public User getById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);

        if(!userOptional.isPresent()){
            throw new DomainException("Username [%s] does not exist please contact administrator.".formatted(id));
        }

        return userOptional.get();
    }

    public void editUserDetails(UUID userId, UserEditRequest userEditRequest){

        User user = getById(userId);

        user.setFullName(userEditRequest.getFullName());
        user.setEmail(userEditRequest.getEmail());
        user.setCountry(userEditRequest.getCountry());
        user.setProfile_picture(userEditRequest.getProfilePicture());

        userRepository.save(user);
    }


    private User initializeUser(RegisterRequest registerRequest){

        return User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(UserRole.USER)
                .isActive(true)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }


}
