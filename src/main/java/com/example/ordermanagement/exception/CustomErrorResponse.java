package com.example.ordermanagement.exception;

import java.time.LocalDateTime;
import java.util.List;

public class CustomErrorResponse {
    private String message;
    private List<String> errors;

    // Constructor, getters, and setters

    public CustomErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
