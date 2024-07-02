package com.systemupdate.beta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systemupdate.beta.models.InformacaoAdicionaisColaborador;

@Repository
public interface InformacaoAddRepository extends JpaRepository<InformacaoAdicionaisColaborador,Long>{
    
} 
