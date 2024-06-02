package com.systemupdate.beta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemupdate.beta.models.Chamado;

public interface ChamadoRepository extends JpaRepository <Chamado,Long> {
    
}
