package com.supplyboost.budgetapp.budget.service;

import com.supplyboost.budgetapp.budget.model.Budget;
import com.supplyboost.budgetapp.budget.repository.BudgetRepository;
import com.supplyboost.budgetapp.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

@Slf4j
@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public void createNewBudget(User user){


        Budget budget = budgetRepository.save(initializeBudget(user));
        log.info("Budget successfully created with id [%s] and currency [%s]."
                .formatted(budget.getId(), budget.getCurrency()));
    }

    private Budget initializeBudget(User user) {
        return Budget.builder()
                .owner(user)
                .name("Private Budget")
                .currency(Currency.getInstance("EUR"))
                .createdOn(LocalDateTime.now())
                .upgradedOn(LocalDateTime.now())
                .build();
    }
}
