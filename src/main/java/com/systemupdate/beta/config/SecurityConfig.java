package com.systemupdate.beta.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.systemupdate.beta.service.UsuarioService;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/materialize/**", "/css/**", "/image/**", "/js/**").permitAll()
                .requestMatchers("/", "/home","/login-redefinir-senha","login-nova-senha","login-recuperar-senha").permitAll() 
                
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/principal", true)
                .failureUrl("/login-error")
                .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe
            .key("uniqueAndSecret")
            .tokenValiditySeconds(86400) // tempo de validade do token (em segundos)
            .userDetailsService(usuarioService)

        )
            .logout(logout -> logout.permitAll())
            .sessionManagement(sessionManagement -> sessionManagement
                .maximumSessions(1) 
                .maxSessionsPreventsLogin(true) 
                .expiredUrl("/login?expired") 
                .sessionRegistry(sessionRegistry()) 
        );
            

        return http.build();
        
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //CRIP SENHA
        auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
    @Bean
    public ServletListenerRegistrationBean<?> servletListenerRegistrationBean(){
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}
