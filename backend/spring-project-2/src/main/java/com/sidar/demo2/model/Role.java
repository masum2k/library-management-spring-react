package com.sidar.demo2.model;

public enum Role {
    USER("USER", "Basic user permissions"),
    LIBRARIAN("LIBRARIAN", "Can manage books and users"),
    ADMIN("ADMIN", "Full system access"),
    SUPER_ADMIN("SUPER_ADMIN", "System administration");

    private final String authority;
    private final String description;

    Role(String authority, String description) {
        this.authority = authority;
        this.description = description;
    }

    public String getAuthority() { return authority; }
    public String getDescription() { return description; }
}