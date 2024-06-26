package com.systemupdate.beta.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.repository.UsuarioRepository;
import com.systemupdate.beta.security.CustomUserDetails;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Optional<Usuario> buscarPorEmailEAtivo(String email) {
        return usuarioRepository.findByEmailAndAtivo(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = buscarPorEmailEAtivo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    
        if (!usuario.isAtivo()) {
            throw new UsernameNotFoundException("Conta desativada. Entre em contato com o suporte.");
        }
    
        List<GrantedAuthority> authorities = usuario.getPerfis().stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getDescricao()))
                .collect(Collectors.toList());
    
        return new CustomUserDetails(
            usuario.getEmail(),
            usuario.getSenha(),
            authorities,
            authorities.isEmpty() ? null : authorities.get(0).getAuthority()
        );
    }
    
    
    
    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario findByEmail(String userEmail) {
        return usuarioRepository.findByEmail(userEmail);
    }


}
