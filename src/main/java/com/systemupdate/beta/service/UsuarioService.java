package com.systemupdate.beta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.repository.UsuarioRepository;
import com.systemupdate.beta.security.CustomUserDetails;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = buscarPorEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        List<GrantedAuthority> authorities = usuario.getPerfis().stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getDesc()))
                .collect(Collectors.toList());

        return new CustomUserDetails(
            usuario.getEmail(),
            usuario.getSenha(),
            authorities,
            authorities.isEmpty() ? null : authorities.get(0).getAuthority()
        );
    }
}
