package ru.flawden.SOAPbackendAPI.entity;

import com.google.common.collect.Sets;
import java.util.Set;

import static ru.flawden.SOAPbackendAPI.entity.Authority.*;

public enum Role {
    USER(Sets.newHashSet(USER_READ)),
    OPERATOR(Sets.newHashSet(USER_READ, USER_WRITE)),
    ANALYST(Sets.newHashSet(USER_READ, STATISTICS_READ, STATISTICS_WRITE)),
    ADMIN(Sets.newHashSet(USERSLIST_READ, USERSLIST_WRITE, STATISTICS_WRITE, STATISTICS_READ));

    private final Set<Authority> authorities;

    Role(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

}
