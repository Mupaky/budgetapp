package com.supplyboost.budgetapp.budget.repository;

import com.supplyboost.budgetapp.budget.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findByCategoryId(UUID categoryId); // Find items for a specific category
    List<Item> findByCategoryIdAndName(UUID categoryId, String name);
}
