package com.jjstudio.util;

public enum RoleEnum {
    FREE_USER("FREE_USER"),
    PAID_USER("PAID_USER"),
    ADMIN("ADMIN");

    String role;

    RoleEnum(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return role;
    }
}
