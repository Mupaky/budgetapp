package com.supplyboost.budgetapp.budget.service;

import com.supplyboost.budgetapp.budget.model.Category;
import com.supplyboost.budgetapp.budget.model.Item;
import com.supplyboost.budgetapp.budget.model.ItemType;
import com.supplyboost.budgetapp.budget.repository.CategoryRepository;
import com.supplyboost.budgetapp.budget.repository.ItemRepository;
import com.supplyboost.budgetapp.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ItemService {

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }


    public void addItemToCategory(UUID categoryId, ItemType type, String itemName
            , BigDecimal price, int period){

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()){
            throw new DomainException("Category not found with id: [%s]".formatted(categoryId));
        }

        Item item = itemRepository.save(initializer(category.get(), type, itemName, price, period));
        log.info("Item [%s] successfully added to category [%s]."
                .formatted(itemName, category.get().getSubcategory()));
    }

    private Item initializer(Category category, ItemType type, String itemName
            , BigDecimal price, int period){
        return Item.builder()
                .category(category)
                .type(type)
                .name(itemName)
                .price(price)
                .period(period)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }
}
