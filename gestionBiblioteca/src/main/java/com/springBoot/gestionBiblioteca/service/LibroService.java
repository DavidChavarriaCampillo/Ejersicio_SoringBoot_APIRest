package com.springBoot.gestionBiblioteca.service;

import java.util.List;

import com.springBoot.gestionBiblioteca.model.Libro;

public interface LibroService extends BaseService<Libro, Integer> {
	public List<Libro> disponibles() throws Exception;
	public Libro estado(Integer id) throws Exception;
}
