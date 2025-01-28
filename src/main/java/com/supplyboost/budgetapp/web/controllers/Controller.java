package com.supplyboost.budgetapp.web.controllers;


import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/info")
public class Controller {


    private Map<Integer, String> users = Map
            .of(1, "Ivan",
            2, "Jorkata",
            3, "Maria");


    @GetMapping("/users/{id}")
    public String getUsernameById(@PathVariable int id, @RequestParam("firstName") String firstName
            , @RequestParam("age") int age){
        return users.get(id);
    }


    //HTTP GET /info/time-now
    //1. What is the time now
    @GetMapping("/time-now")
    public String getTimeNow(){
        return "Time now is " + LocalDateTime.now();
    }


    //HTTP GET /info/today
    //2. Day of the week
    @GetMapping("/today")
    public String getDayOfWeek(){
        return "Today is " + LocalDateTime.now().getDayOfWeek().name();
    }
}
