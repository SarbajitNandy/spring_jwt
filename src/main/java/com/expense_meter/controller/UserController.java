package com.expense_meter.controller;

import com.expense_meter.dto.UserDTO;
import com.expense_meter.utils.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class UserController {
    @GetMapping("/{uuid}")
    public ResponseEntity welcome(@PathVariable("uuid") String uuid, @AuthenticationPrincipal(expression = "user") UserDTO user) {
        System.out.println(user.toString());
        return ResponseEntity.ok("Welcome " + uuid);
    }
}
