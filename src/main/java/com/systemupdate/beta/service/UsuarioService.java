package com.systemupdate.beta.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.repository.UsuarioRepository;
import com.systemupdate.beta.security.CustomUserDetails;

import jakarta.mail.MessagingException;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Optional<Usuario> buscarPorEmailEAtivo(String email) {
        return usuarioRepository.findByEmailAndAtivo(email);
    }

    @Transactional
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
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

    @Transactional(readOnly = false)
    public void pedidoRefinicaoDeSenha(String email) throws MessagingException {
        Usuario usuario = buscarPorEmailEAtivo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        String verificador = generateRandomAlphanumeric(6);
        usuario.setCodigoVerificador(verificador);

        emailService.enviarPedidoRedefinicaoSenha(email, verificador);
    }

    public void alterarSenha(Usuario u, String senha) {
         // Criptografa a senha usando BCrypt
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String senhaCriptografada = encoder.encode(senha);
    
    // Define a senha criptografada no usuário
    u.setSenha(senhaCriptografada);
    
    // Salva o usuário no banco de dados
    usuarioRepository.save(u);
    }

    private String generateRandomAlphanumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
