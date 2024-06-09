package com.systemupdate.beta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemupdate.beta.models.ChamadoFinanceiro;

public interface ChamadoFinanceiroRepository extends JpaRepository<ChamadoFinanceiro, Long>{
    
}
