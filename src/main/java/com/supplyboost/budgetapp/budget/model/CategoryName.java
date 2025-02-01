package com.supplyboost.budgetapp.budget.model;

public enum CategoryName {

    SALARY("Salary"),
    BENEFITS("Benefits"),
    TAX("Tax"),
    PENSION("Pension"),
    BUSINESS("Business"),
    OTHER("Other"),
    ADDITIONAL("Additional"),

    HOUSEHOLD("HouseHold"),
    LIVING("Living"),
    FINANCE("Finance"),
    INSURANCE("Insurance"),
    FAMILY("Family"),
    FRIENDS("Friends"),
    TRAVEL("Travel"),
    LEISURE("Leisure");



    private final String displayName;

    CategoryName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
