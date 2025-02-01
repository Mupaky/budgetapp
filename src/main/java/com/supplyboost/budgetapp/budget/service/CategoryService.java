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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Transactional
    public Category addCategoryToBudget(Budget budget, CategoryName categoryName) {

        Category category = categoryRepository.save(initializer(budget, categoryName));
        log.info("Category [%s] successfully created with budget {id: [%s]} with and {id: %s]}."
                .formatted(categoryName, budget.getId(), category.getId()));

        return category;
    }

    private Category initializer(Budget budget, CategoryName categoryName){
        return Category.builder()
                .subcategory(categoryName)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .budget(budget)
                .build();
    }
}
