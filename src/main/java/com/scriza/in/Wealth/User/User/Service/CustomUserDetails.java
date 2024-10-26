package com.scriza.in.Wealth.User.User.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scriza.in.Wealth.User.User.Entity.User;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Adjust according to your needs
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust according to your needs
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust according to your needs
    }

    @Override
    public boolean isEnabled() {
        return true; // Adjust according to your needs
    }

    public User getUser() {
        return user;
    }
}