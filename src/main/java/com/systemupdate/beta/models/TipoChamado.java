package com.systemupdate.beta.models;



import jakarta.persistence.*;
/*
 * 1 - INFORMATICA
 * 2 - JURIDICO
 * 3 - FINANCEIRO
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "tipo_chamado")
public class TipoChamado extends AbstractEntity {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
