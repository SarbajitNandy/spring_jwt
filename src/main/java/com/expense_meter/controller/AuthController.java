package com.expense_meter.controller;

import com.expense_meter.dto.AuthRequest;
import com.expense_meter.dto.AuthResponse;
import com.expense_meter.dto.ErrorResponse;
import com.expense_meter.dto.UserDTO;
import com.expense_meter.services.UserService;
import com.expense_meter.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(consumes = "application/json", value="/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDTO reqBody, Errors errors) {
//        User user = User.createUser(reqBody);
        System.out.println("Reached");

        if (errors.hasErrors()) {
            String errorMsg = errors.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            return ResponseEntity.ok(new ErrorResponse(errorMsg,400));
        }
        try {
            String password = reqBody.getPassword();
            String encryPassWord = bCryptPasswordEncoder.encode(password);
            reqBody.setPassword(encryPassWord);
            System.out.println(reqBody);
            UserDTO newUser = userService.insertUser(reqBody);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping(consumes = "application/json", value="/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest, Errors errors) {
        if (errors.hasErrors()) {
            final String errorMessage = errors.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(","));
            return ResponseEntity.ok(new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value()));
        }
        System.out.println("starting with authentication");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch(AuthenticationException e) {
            return ResponseEntity.ok(new ErrorResponse("Unauthorized", HttpStatus.UNAUTHORIZED.value()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN.value()));
        }
        UserDTO u = userService.findByEmail(authRequest.getEmail());
        Map<String, Object> claims = new HashMap<>();
        claims.put("uuid", u.getUuid());

        UserDetails user = new User(authRequest.getEmail(),authRequest.getPassword(), new ArrayList<>());

        final String token = jwtUtils.generateToken(user, claims);
        System.out.println("token " + token);

        return ResponseEntity.ok(new AuthResponse(u.getUuid(), u.getName(), token));

    }
}
