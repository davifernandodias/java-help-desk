package com.systemupdate.beta.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamado_juridico")
public class ChamadoJuridico {
    @Id
    private Long id;
    
    @Column(name = "advogado", nullable = false)
    private String advogado;
    @Column(name = "processo", nullable = false)
    private String processo;
    @Column(name = "id_tipo_chamado", nullable = false)
    private int idTipoChamado;
    
    public int getIdTipoChamado() {
        return idTipoChamado;
    }
    public void setIdTipoChamado(int idTipoChamado) {
        this.idTipoChamado = idTipoChamado;
    }
    public String getAdvogado() {
        return advogado;
    }
    public String getProcesso() {
        return processo;
    }
    public void setAdvogado(String advogado) {
        this.advogado = advogado;
    }
    public void setProcesso(String processo) {
        this.processo = processo;
    }
    public Long getId() {
        return id;
    }
   

    public void setId(Long id) {
        this.id = id;
    }
   
}
