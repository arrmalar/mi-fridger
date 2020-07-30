package com.meefri.app.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;
    private String password;
    private String username;
    private String email;
    private UserFridge userFridge;
    private List<String> userCreatedRecipes;
    private List<String> userLikedRecipes;
    private UserPersonalInformation userPersonalInformation;
    private UserAccountInformation userAccountInformation;
    private UserActivityInformation userActivityInformation;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
