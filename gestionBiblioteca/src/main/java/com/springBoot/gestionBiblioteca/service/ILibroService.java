package com.springBoot.gestionBiblioteca.service;

import org.springframework.data.repository.CrudRepository;

import com.springBoot.gestionBiblioteca.model.Libro;

public interface ILibroService extends CrudRepository<Libro, Integer> {

}
