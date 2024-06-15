package com.systemupdate.beta.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chamados")
public class Chamado extends AbstractEntity {

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "supervisor", nullable = false)
    private String supervisor;

    @Column(name = "tipo_de_chamado", nullable = false)
    private int tipoDeChamado;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_colaborador", nullable = false)
    private Colaborador colaborador;

    // Atributos específicos de cada tipo de chamado

    // INFORMATICA
   /* @Column(name = "problema")
    private String problema;

    @Column(name = "equipamento")
    private String equipamento;

    // FINANCEIRO
    @Column(name = "valor")
    private Integer valor;

    @Column(name = "conta")
    private String conta;

    // JURIDICO
    @Column(name = "processo")
    private String processo;

    @Column(name = "advogado")
    private String advogado;
 */
    // Getters e Setters omitidos para brevidade

    

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
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

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

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

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }



    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
