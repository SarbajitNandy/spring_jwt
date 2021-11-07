package com.expense_meter.dto;

public class AuthResponse {
    private final String token;

    private final String uuid,username;

    public String getToken() {
        return token;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public AuthResponse(String uuid, String username, String token) {
        this.token = token;
        this.uuid = uuid;
        this.username = username;
    }
}
