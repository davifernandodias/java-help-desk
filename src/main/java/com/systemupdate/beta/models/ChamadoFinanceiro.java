package com.systemupdate.beta.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamado_financeiro")
public class ChamadoFinanceiro {
    @Id
    private Long id;
    
    @Column(name = "valor", nullable = false)
    private int valor;
    @Column(name = "conta", nullable = false)
    private String conta;
    @Column(name = "id_tipo_chamado_referencia", nullable = false)
    private int idTipoChamado;

    public int getIdTipoChamado() {
        return idTipoChamado;
    }
    public void setIdTipoChamado(int idTipoChamado) {
        this.idTipoChamado = idTipoChamado;
    }

    public String getConta() {
        return conta;
    }
    public Long getId() {
        return id;
    }
    public int getValor() {
        return valor;
    }
    public void setConta(String conta) {
        this.conta = conta;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
}
