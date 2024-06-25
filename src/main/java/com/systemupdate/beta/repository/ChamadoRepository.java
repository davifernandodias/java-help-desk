package com.systemupdate.beta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.models.Colaborador;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    Iterable<Chamado> findByColaborador(Colaborador colaborador);

    @Query("SELECT COUNT(c) FROM Chamado c WHERE c.status = :status")
    int countByStatus(String status);

    @Query("SELECT c FROM Chamado c WHERE c.colaborador = :colaborador ORDER BY "
            + "CASE c.status "
            + "WHEN 'aberto' THEN 1 "
            + "WHEN 'andamento' THEN 2 "
            + "WHEN 'finalizado' THEN 3 "
            + "ELSE 4 END")
    List<Chamado> findByColaboradorOrderByStatus(@Param("colaborador") Colaborador colaborador);

    @Query("SELECT c FROM Chamado c ORDER BY "
            + "CASE c.status "
            + "WHEN 'aberto' THEN 1 "
            + "WHEN 'andamento' THEN 2 "
            + "WHEN 'finalizado' THEN 3 "
            + "ELSE 4 END")
    List<Chamado> findAllOrderByStatus();
}
