package ru.flawden.SOAPbackendAPI.entity;

public enum Authority {

    STATISTICS_READ("statistics:read"),
    STATISTICS_WRITE("statistics:write"),
    USERSLIST_READ("users:read"),
    USERSLIST_WRITE("users:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    Authority(String permission) {
        this.permission = permission;
    }

}
