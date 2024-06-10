package com.systemupdate.beta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.models.Colaborador;

public interface ChamadoRepository extends JpaRepository <Chamado,Long> {
    Iterable<Chamado> findByColaborador(Colaborador colaborador);
    
    @Query("SELECT COUNT(c) FROM Chamado c WHERE c.status = :status")
    int countByStatus(String status);
}
