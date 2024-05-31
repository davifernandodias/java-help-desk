package com.systemupdate.beta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemupdate.beta.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u from Usuario u where u.email = :email")
    Usuario findByEmail(@Param("email") String email);
}
