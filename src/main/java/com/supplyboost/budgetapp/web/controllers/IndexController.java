package com.supplyboost.budgetapp.web.controllers;


import com.supplyboost.budgetapp.user.model.User;
import com.supplyboost.budgetapp.user.repository.UserRepository;
import com.supplyboost.budgetapp.user.service.UserService;
import com.supplyboost.budgetapp.web.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }


    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        if (auth != null && auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken)) {
            modelAndView.setViewName("redirect:/dashboard");
            return modelAndView;
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("register");
    }


    @PostMapping("/register")
    public ModelAndView processRegistration(@RequestParam String username, @RequestParam String email,
            @RequestParam String password) {
        try {
            RegisterRequest registerRequest = RegisterRequest.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
            userService.register(registerRequest);
            return new ModelAndView("redirect:/login?registered=true");
        } catch (RuntimeException e) {
            return new ModelAndView("register");
        }
    }


    @GetMapping("/dashboard")
    public ModelAndView showDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard");
        if (currentUser.isPresent()){
            modelAndView.addObject("user", currentUser.get());
        }

        return modelAndView;
    }

}
