package com.springBoot.gestionBiblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario {
	
	@Id
	@NotBlank
	private String documento;
	
	@NotBlank
	private String nombre;
	
	public Usuario() {
	}

	public Usuario(String documento, String nombre) {
		this.documento = documento;
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}
