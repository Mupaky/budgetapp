package com.supplyboost.budgetapp.user.service;

import com.supplyboost.budgetapp.user.model.User;
import com.supplyboost.budgetapp.user.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Expose the user entity to the rest of the application if needed
    public User getUserEntity() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For simplicity, map your Enum role to Spring Security's authority
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // You could tie in business logic here. For now, return true.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // e.g., if user tries too many logins, you might lock them
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return true if the password is still valid
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
