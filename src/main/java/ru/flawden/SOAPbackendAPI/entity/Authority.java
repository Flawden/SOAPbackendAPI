package ru.flawden.SOAPbackendAPI.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    STATISTICS_READ("statistics:read"),
    STATISTICS_WRITE("statistics:write"),
    USERSLIST_READ("users:read"),
    USERSLIST_WRITE("users:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String authorities;

    Authority(String permission) {
        this.authorities = permission;
    }

    @Override
    public String getAuthority() {
        return authorities;
    }
}
