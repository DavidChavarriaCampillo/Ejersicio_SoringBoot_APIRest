package com.springBoot.gestionBiblioteca.model;

import jakarta.persistence.*;

@Entity
public class Multa{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int valor;

	public Multa() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
}
