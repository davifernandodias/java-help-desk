package com.systemupdate.beta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemupdate.beta.models.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil,Long>{
    Perfil findByDescricao(String descricao);
    
}
