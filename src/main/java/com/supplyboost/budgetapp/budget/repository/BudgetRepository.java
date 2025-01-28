package com.supplyboost.budgetapp.budget.repository;

import com.supplyboost.budgetapp.budget.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

}
