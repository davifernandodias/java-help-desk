package com.systemupdate.beta.models;

import jakarta.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamado_informatica")
public class ChamadoInformatica extends AbstractEntity {

    @Column(name = "problema", nullable = false)
    private String problema;

    @Column(name = "equipamento", nullable = false)
    private String equipamento;

    @Column(name = "tipo_chamado", nullable = false)
    private int tipoChamado; // Adicionar o campo tipoChamado

    @OneToOne
    @JoinColumn(name = "tipo_chamado", referencedColumnName = "tipo_chamado", insertable = false, updatable = false)
    private Chamado chamado;


    // Getters and Setters
    public int getTipoChamado() {
        return tipoChamado;
    }
    public void setTipoChamado(int tipoChamado) {
        this.tipoChamado = tipoChamado;
    }
    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }
}
