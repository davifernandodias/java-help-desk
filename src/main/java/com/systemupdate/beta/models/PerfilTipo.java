package com.systemupdate.beta.models;

public enum PerfilTipo {
    ADMIN(1, "ADMIN"), 
    COLABORADOR(3, "COLABORADOR");

    private long cod;
    private String descricao;

    private PerfilTipo(long cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public long getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }
}
