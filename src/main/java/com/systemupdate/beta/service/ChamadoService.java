package com.systemupdate.beta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.repository.ChamadoRepository;

import jakarta.transaction.Transactional;

@Service
public class ChamadoService {
    @Autowired
    private ChamadoRepository chamadoRepository;
    
    @Transactional
    public void salvar(Chamado chamado){
        chamadoRepository.save(chamado);
    }
}
