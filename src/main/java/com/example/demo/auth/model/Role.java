package com.example.demo.auth.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

    USER(Set.of(
            Permission.STUDENT_READ
    )),

    ADMIN(Set.of(
            Permission.STUDENT_READ,
            Permission.STUDENT_CREATE,
            Permission.STUDENT_UPDATE,
            Permission.STUDENT_DELETE
    ));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities =
                permissions.stream()
                        .map(permission ->
                                new SimpleGrantedAuthority(permission.getPermission()))
                        .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
