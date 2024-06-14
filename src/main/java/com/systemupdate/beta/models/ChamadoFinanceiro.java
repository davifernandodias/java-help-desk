package com.systemupdate.beta.models;

import jakarta.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamado_financeiro")
public class ChamadoFinanceiro extends AbstractEntity {

    @Column(name = "valor", nullable = false)
    private int valor;

    @Column(name = "conta", nullable = false)
    private String conta;

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
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }
}
