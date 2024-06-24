package com.systemupdate.beta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.systemupdate.beta.models.Colaborador;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
    
}
