package com.systemupdate.beta.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco extends AbstractEntity {

    @Column(name = "logadouro")
    private String logradouro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "estado")
    private String estado;
    @Column(name = "barrio")
    private String bairro;
    @Column(name = "rua")
    private String rua;
    @Column(name = "numero")
    private String numero;
    @Column(name = "cep")
    private String cep;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_informacao_adicionais", nullable = false)
    private InformacaoAdicionaisColaborador informacaoAdicionaisColaborador;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public InformacaoAdicionaisColaborador getInformacaoAdicionaisColaborador() {
        return informacaoAdicionaisColaborador;
    }

    public void setInformacaoAdicionaisColaborador(InformacaoAdicionaisColaborador informacaoAdicionaisColaborador) {
        this.informacaoAdicionaisColaborador = informacaoAdicionaisColaborador;
    }

}
