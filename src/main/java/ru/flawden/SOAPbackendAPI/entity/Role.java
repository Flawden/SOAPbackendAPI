package ru.flawden.SOAPbackendAPI.entity;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.flawden.SOAPbackendAPI.entity.Permission.*;

public enum Role {

    USER(Sets.newHashSet(USER_READ)),
    OPERATOR(Sets.newHashSet(USER_READ, USER_WRITE)),
    ANALYST(Sets.newHashSet(USER_READ, STATISTICS_READ, STATISTICS_WRITE)),
    ADMIN(Sets.newHashSet(USERSLIST_READ, USERSLIST_WRITE, STATISTICS_WRITE, STATISTICS_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
