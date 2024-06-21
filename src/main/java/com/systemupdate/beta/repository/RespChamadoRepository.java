package com.systemupdate.beta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.models.RespChamado;

public interface RespChamadoRepository extends JpaRepository<RespChamado, Long> {
     RespChamado findByChamado(Chamado chamado);
}
