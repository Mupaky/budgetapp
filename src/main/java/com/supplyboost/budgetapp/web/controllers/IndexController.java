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

import java.util.Optional;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    /**
     * HOME PAGE (public)
     */
    @GetMapping("/")
    public String showHomePage() {
        return "home"; // => templates/home.html
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";  // => templates/about.html
    }

    /**
     * LOGIN PAGE (public, but only if not authenticated)
     */
    @GetMapping("/login")
    public String showLoginPage() {
        // If user is already authenticated, redirect to dashboard
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        }
        return "login"; // => templates/login.html
    }

    /**
     * REGISTER PAGE (public)
     */
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // => templates/register.html
    }

    /**
     * Handle Registration
     */
    @PostMapping("/register")
    public String processRegistration(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
    ) {
        try {
            RegisterRequest registerRequest = RegisterRequest.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
            userService.register(registerRequest);
            return "redirect:/login?registered=true";
        } catch (RuntimeException e) {
            return "register";
        }
    }

    /**
     * DASHBOARD (authenticated users only)
     */
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Optionally retrieve current user and pass to the model
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> currentUser = userRepository.findByUsername(username);

        if (currentUser.isPresent()){
            model.addAttribute("user", currentUser.get());
        }

        return "dashboard"; // => templates/dashboard.html
    }

    /**
     * ADD CATEGORY (authenticated)
     */
    @GetMapping("/add-category")
    public String showAddCategory() {
        return "add-category"; // => templates/add-category.html
    }

    @PostMapping("/add-category")
    public String handleAddCategory(/* receive fields */) {
        // e.g. create category for the user's budget
        return "redirect:/dashboard";
    }

    /**
     * ADD ITEM (authenticated)
     */
    @GetMapping("/add-item")
    public String showAddItem() {
        return "add-item"; // => templates/add-item.html
    }

    @PostMapping("/add-item")
    public String handleAddItem(/* fields */) {
        // e.g. create item for the user's category
        return "redirect:/dashboard";
    }
}
