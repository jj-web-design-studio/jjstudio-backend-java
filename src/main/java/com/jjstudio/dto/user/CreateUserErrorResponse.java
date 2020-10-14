package com.jjstudio.dto.user;

public class CreateUserErrorResponse {

    private String message;

    public CreateUserErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
