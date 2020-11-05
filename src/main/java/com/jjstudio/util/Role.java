package com.jjstudio.util;

public enum Role {

    FREE_USER("FREE_USER"),
    PAID_USER("PAID_USER"),
    ADMIN("ADMIN");

    String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

}
