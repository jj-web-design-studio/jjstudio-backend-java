package com.jjstudio.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Integer id) {
        super("The user with id " + id + " does not exist");
    }
}
