package com.systemupdate.beta.models;

import jakarta.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "perfis")
public class Perfil extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, unique = true)
    private String descricao;

    public Perfil() {
        super();
    }

    public Perfil(Long id) {
        super.setId(id);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
