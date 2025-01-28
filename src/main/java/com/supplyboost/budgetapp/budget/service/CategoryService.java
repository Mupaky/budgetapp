package com.supplyboost.budgetapp.budget.service;

import com.supplyboost.budgetapp.budget.model.Budget;
import com.supplyboost.budgetapp.budget.model.Category;
import com.supplyboost.budgetapp.budget.model.CategoryName;
import com.supplyboost.budgetapp.budget.repository.BudgetRepository;
import com.supplyboost.budgetapp.budget.repository.CategoryRepository;
import com.supplyboost.budgetapp.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CategoryService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addCategoryToBudget(UUID budgetId, CategoryName categoryName) {
        Optional<Budget> budget = budgetRepository.findById(budgetId);

        if (budget.isEmpty()){
            throw new DomainException("Budget not found with id: [%s]".formatted(budgetId));
        }

        Category category = categoryRepository.save(initializer(budget.get(), categoryName));
        log.info("Category [%s] successfully added to budget [%s] with id [%s]."
                .formatted(categoryName, budgetId, category.getId()));
    }

    private Category initializer(Budget budget, CategoryName categoryName){
        return Category.builder()
                .subcategory(categoryName)
                .budget(budget)
                .build();
    }
}
