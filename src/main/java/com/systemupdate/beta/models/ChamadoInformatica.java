package com.systemupdate.beta.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamado_informatica")
public class ChamadoInformatica {
    @Id
    private Long id;
    
    @Column(name = "problema", nullable = false)
    private String problema;
    @Column(name = "equipamento", nullable = false)
    private String equipamento;
    @Column(name = "id_tipo_chamado_referencia", nullable = false)
    private int idTipoChamado;
    
    
    public int getIdTipoChamado() {
        return idTipoChamado;
    }
    public void setIdTipoChamado(int idTipoChamado) {
        this.idTipoChamado = idTipoChamado;
    }
    
    public String getEquipamento() {
        return equipamento;
    }

    public Long getId() {
        return id;
    }
    public String getProblema() {
        return problema;
    }
    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setProblema(String problema) {
        this.problema = problema;
    }
}
