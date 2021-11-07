package com.expense_meter.services;

import com.expense_meter.dto.UserDTO;
import com.expense_meter.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            UserDTO user = userService.findByEmail(username);
            return new CustomUserDetails(user);

        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("No user exists with email id " + username);
        }

    }
}
