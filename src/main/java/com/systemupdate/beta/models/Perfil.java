package com.systemupdate.beta.models;

import jakarta.persistence.*;

@Entity
@Table(name = "perfis")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    public Perfil() {
    }

    public Perfil(Long id) {
        this.id = id;
    }

    public Perfil(String descricao) {
        this.descricao = descricao;
    }

    public Perfil(int cod) {
        this.descricao = PerfilTipo.toEnum(cod).getDescricao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
