package com.systemupdate.beta.models;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class ChamadoBase extends AbstractEntity {

    @Column(name = "tipo_chamado", nullable = false)
    private int tipoDeChamado;

    @Column(name = "status", nullable = false)
    private String status = "aberto";

    @Column(name = "data_criacao", nullable = false)
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_colaborador", nullable = false)
    private Colaborador colaborador;

    // Getters and Setters
    public int getTipoDeChamado() {
        return tipoDeChamado;
    }
    public void setTipoDeChamado(int tipoDeChamado) {
        this.tipoDeChamado = tipoDeChamado;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
}
