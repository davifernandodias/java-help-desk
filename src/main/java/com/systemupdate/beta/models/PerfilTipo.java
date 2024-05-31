package com.systemupdate.beta.models;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), COLABORADOR(3, "COLABORADOR");
	
	private long cod;
	private String desc;

	private PerfilTipo(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public long getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
}
