package com.expense_meter.dto;

public class ErrorResponse {
    private final String message;
    private final int statusCode;

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ErrorResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
