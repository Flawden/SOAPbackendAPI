package ru.flawden.soapbackendapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    public Set<Authority> getAuthorities() {
        Set<Authority> authorities = new HashSet<Authority>();
        roles.stream().forEach(role -> role.getAuthorities().stream().forEach(authority -> authorities.add(authority)));
        return authorities;
    }

}
