package com.supplyboost.budgetapp.budget.repository;

import com.supplyboost.budgetapp.budget.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
