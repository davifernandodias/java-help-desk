package com.systemupdate.beta.models;

public enum PerfilTipo {
    ADMIN(1, "ADMIN"),
    COLABORADOR(2, "COLABORADOR");

    private int cod;
    private String descricao;

    private PerfilTipo(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static PerfilTipo toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (PerfilTipo x : PerfilTipo.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
