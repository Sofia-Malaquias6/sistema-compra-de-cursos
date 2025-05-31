package com.trabalho.sistemacompradecursos.security;

import com.trabalho.sistemacompradecursos.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_USER");
    }

    @Override
    public String getPassword() {
        return user.getSenha(); // supondo que você tem um campo password
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // login baseado no email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // controle se necessário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // controle se necessário
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // controle se necessário
    }

    @Override
    public boolean isEnabled() {
        return true; // controle se necessário
    }

    public Long getId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }
}
