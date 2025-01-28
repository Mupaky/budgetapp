package com.supplyboost.budgetapp.web.dto;

import com.supplyboost.budgetapp.budget.model.CategoryName;
import lombok.Data;

@Data
public class CreateCategoryRequest {

    private CategoryName categoryName;

}
