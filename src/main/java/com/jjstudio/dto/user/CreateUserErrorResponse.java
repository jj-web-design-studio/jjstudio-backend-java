package com.jjstudio.dto.user;

public class CreateUserErrorResponse {

    private String email;

    private String message;

    public CreateUserErrorResponse(String email) {
        this.email = email;
        this.message = "A user with the email " + email + " already exists";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
