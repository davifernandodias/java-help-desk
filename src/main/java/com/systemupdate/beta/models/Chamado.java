package com.systemupdate.beta.models;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamados")
public class Chamado extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "tipo_de_chamado", nullable = false)
    private String tipoDeChamado;

    @Column(name = "descricao", nullable = false)
    private String descricao;
    
    @Column(name = "supervisor", nullable = false)
    private String supervisor;

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

    
    public String getTipoDeChamado() {
        return tipoDeChamado;
    }
    public void setTipoDeChamado(String tipoDeChamado) {
        this.tipoDeChamado = tipoDeChamado;
    }
    public String getSupervisor() {
        return supervisor;
    }
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
