package com.systemupdate.beta.models;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reposta_admin")
public class RespChamado extends AbstractEntity {
    @Column(name = "chat_admin")
    private String respoAdmin;

     @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataDeEnvio;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_chamado", nullable = false)
    private Chamado chamado;

    public LocalDateTime getDataDeEnvio() {
        return dataDeEnvio;
    }
    public void setDataDeEnvio(LocalDateTime dataDeEnvio) {
        this.dataDeEnvio = dataDeEnvio;
    }
    public String getRespoAdmin() {
        return respoAdmin;
    }
    public void setRespoAdmin(String respoAdmin) {
        this.respoAdmin = respoAdmin;
    }
    public Chamado getChamado() {
        return chamado;
    }
    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }
}
