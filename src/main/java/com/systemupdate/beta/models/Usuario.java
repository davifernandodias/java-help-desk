package com.systemupdate.beta.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "usuarios", indexes = {@Index(name = "idx_usuario_email", columnList = "email")})
public class Usuario extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "senha", nullable = false)
    private String senha;

    @ManyToMany
    @JoinTable(
        name = "usuarios_tem_perfis",
        joinColumns = { @JoinColumn(name = "usuario_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "perfil_id", referencedColumnName = "id") }
    )
    private List<Perfil> perfis;

    @Column(name = "ativo", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean ativo;

    @Column(name = "codigo_verificador", length = 6)
    private String codigoVerificador;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Colaborador colaborador;

    public Usuario() {
        super();
    }

    public Usuario(Long id) {
        super.setId(id);
    }

    public Usuario(String email) {
        this.email = email;
    }

    // Adiciona perfis à lista
    public void addPerfil(PerfilTipo tipo) {
        if (this.perfis == null) {
            this.perfis = new ArrayList<>();
        }
        this.perfis.add(new Perfil(tipo.getCod()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getCodigoVerificador() {
        return codigoVerificador;
    }

    public void setCodigoVerificador(String codigoVerificador) {
        this.codigoVerificador = codigoVerificador;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public boolean hasPerfil(String perfilDescricao) {
        for (Perfil perfil : perfis) {
            if (perfil.getDescricao().equalsIgnoreCase(perfilDescricao)) {
                return true;
            }
        }
        return false;
    }
}
