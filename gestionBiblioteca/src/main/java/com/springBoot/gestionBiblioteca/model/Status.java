package com.springBoot.gestionBiblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Status {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}		
}
