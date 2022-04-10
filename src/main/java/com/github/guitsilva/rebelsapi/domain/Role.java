package com.github.guitsilva.rebelsapi.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("ROLE_ADMIN"),
    REBEL("ROLE_REBEL");

    private final String authority;

    private Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }
}
