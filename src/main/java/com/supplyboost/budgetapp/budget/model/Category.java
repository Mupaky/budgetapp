package com.supplyboost.budgetapp.budget.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Budget budget;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryName subcategory;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private List<Item> items = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime updatedOn;
}
