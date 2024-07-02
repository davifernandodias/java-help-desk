package com.systemupdate.beta.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.repository.ColaboradorRepository;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<Colaborador> buscarTodos() {
        return colaboradorRepository.findAll();
    }

    public Colaborador buscarPorId(Long id) {
        return colaboradorRepository.findById(id).orElseThrow();
    }

    
    
}
