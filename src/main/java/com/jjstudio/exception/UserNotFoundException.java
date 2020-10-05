package com.jjstudio.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String id) {
        super("The user with id " + id + " does not exist");
    }

}
