package com.systemupdate.beta.models;

import jakarta.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamado_juridico")
public class ChamadoJuridico extends AbstractEntity {

    @Column(name = "advogado", nullable = false)
    private String advogado;

    @Column(name = "processo", nullable = false)
    private String processo;

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
    public String getAdvogado() {
        return advogado;
    }

    public void setAdvogado(String advogado) {
        this.advogado = advogado;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }
}
