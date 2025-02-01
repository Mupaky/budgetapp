package com.supplyboost.budgetapp.subscription.service;

import com.supplyboost.budgetapp.subscription.model.Subscription;
import com.supplyboost.budgetapp.subscription.model.SubscriptionPeriod;
import com.supplyboost.budgetapp.subscription.model.SubscriptionStatus;
import com.supplyboost.budgetapp.subscription.model.SubsriptionType;
import com.supplyboost.budgetapp.subscription.repository.SubscriptionRepository;
import com.supplyboost.budgetapp.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription createDefaultSubscription(User user){

        Subscription subscription = subscriptionRepository.save(initializeSubscription(user));
        log.info("Successfully create new subscription with id [%s] and type [%s]"
                .formatted(subscription.getId(), subscription.getType()));

        return subscription;
    }


    private Subscription initializeSubscription(User user){

        LocalDateTime now = LocalDateTime.now();

        return Subscription.builder()
                .owner(user)
                .type(SubsriptionType.DEFAULT)
                .period(SubscriptionPeriod.MONTHLY)
                .status(SubscriptionStatus.ACTIVE)
                .price(new BigDecimal("0.00"))
                .auto_renew(true)
                .createdOn(now)
                .expiredOn(now.plusMonths(1))
                .build();
    }
}
