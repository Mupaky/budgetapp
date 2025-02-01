package com.supplyboost.budgetapp.web.controllers;

import com.supplyboost.budgetapp.budget.model.Budget;
import com.supplyboost.budgetapp.budget.service.BudgetService;
import com.supplyboost.budgetapp.user.model.User;
import com.supplyboost.budgetapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    private final UserRepository userRepository;

    @Autowired
    public BudgetController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @GetMapping
    public ModelAndView viewBudgets() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);

        if (currentUser.isEmpty()) {
            return new ModelAndView("budgets");
        }

        User user = currentUser.get();
        Budget budget = user.getBudgets().get(0);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("budgets");
        modelAndView.addObject("user", user);
        modelAndView.addObject("budget", budget);

        return modelAndView;
    }


}
