package com.meefri.app.security;

import com.meefri.app.data.User;
import com.meefri.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class LoggedUser {

    private final UserRepository userRepository;

    public LoggedUser(@Autowired UserRepository userRepository){

        this.userRepository = userRepository;
    }

    public User getLoggedUser(){

        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return userRepository.findByUsername(username);

    }
}
