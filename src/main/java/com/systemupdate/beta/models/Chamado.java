package com.systemupdate.beta.models;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.*;
import java.time.LocalDate;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamados") 
public class Chamado extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name="id_colaborador")
	private Colaborador colaborador;

	@Column(name="data_consulta")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataChamado;

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public LocalDate getDataChamado() {
		return dataChamado;
	}

	public void setDataChamado(LocalDate dataChamado) {
		this.dataChamado = dataChamado;
	}
}
