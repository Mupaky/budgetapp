package com.supplyboost.budgetapp.budget.service;

import com.supplyboost.budgetapp.budget.model.Budget;
import com.supplyboost.budgetapp.budget.model.Category;
import com.supplyboost.budgetapp.budget.model.CategoryName;
import com.supplyboost.budgetapp.budget.repository.BudgetRepository;
import com.supplyboost.budgetapp.user.model.User;
import com.supplyboost.budgetapp.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository,
                         CategoryService categoryService,
                         UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Budget createNewBudget(User user){

        Budget budget = budgetRepository.save(initializeBudget(user));
        log.info("Budget successfully created with id [%s] and currency [%s]."
                .formatted(budget.getId(), budget.getCurrency()));

        initialCategories(budget);

//        user.getBudgets().add(budget);
//        userRepository.save(user);

        log.info("user with name:[%s] is register.".formatted(user.getUsername()));


        return budget;
    }

    public List<Budget> findAllByUser(UUID ownerId){
        return budgetRepository.findAllByOwnerId(ownerId);
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

    private void initialCategories(Budget budget){

        try {
            List<Category> categories = new ArrayList<>();
            // Income
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.SALARY));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.BENEFITS));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.TAX));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.PENSION));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.BUSINESS));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.OTHER));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.ADDITIONAL));

            // Spending
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.HOUSEHOLD));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.LIVING));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.FINANCE));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.INSURANCE));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.FAMILY));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.FRIENDS));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.TRAVEL));
            categories.add(categoryService.addCategoryToBudget(budget, CategoryName.LEISURE));

            budget.setCategories(categories);
            log.info("Categories added to budget: {}", budget.getCategories());
            budgetRepository.save(budget);
            log.info("Done");

        } catch (Exception e) {
            log.error("Error creating categories: " + e.getMessage(), e);
        }
    }
}
