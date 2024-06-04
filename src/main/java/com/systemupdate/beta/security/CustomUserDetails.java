package com.systemupdate.beta.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
/*
 * SALVAR ITEMS ADICIONAIS DE USUARIOS
 */
public class CustomUserDetails extends User {
    private final String role;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String role) {
        super(username, password, authorities);
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
