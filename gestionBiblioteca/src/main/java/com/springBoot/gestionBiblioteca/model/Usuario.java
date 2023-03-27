package com.springBoot.gestionBiblioteca.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String documento;
	
	@NotBlank
	private String nombre;
	
	@OneToMany
	private List<Prestamo> prestamos = new ArrayList<>();
	
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

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamos.add(prestamo);
	}
	
	
}
