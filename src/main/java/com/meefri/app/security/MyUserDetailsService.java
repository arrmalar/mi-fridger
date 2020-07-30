package com.meefri.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.meefri.app.data.User;
import com.meefri.app.repositories.UserRepository;

@Service(value = "myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDetails user = userRepository.findByUsername(s);

        if(user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User does not exist!");
        }
    }
}
