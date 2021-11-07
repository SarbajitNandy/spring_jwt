package com.expense_meter.dto;


import javax.validation.constraints.NotNull;

public class AuthRequest {
    @NotNull(message = "User Email can not be null")
    private String email;
    @NotNull(message = "User Password can not be null")
    private String password;

    public AuthRequest(String userEmail, String passWord) {
        this.email = userEmail;
        this.password = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
