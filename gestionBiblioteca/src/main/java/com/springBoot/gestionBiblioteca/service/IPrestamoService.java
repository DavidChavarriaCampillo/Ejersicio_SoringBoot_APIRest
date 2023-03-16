package com.springBoot.gestionBiblioteca.service;

import org.springframework.data.repository.CrudRepository;

import com.springBoot.gestionBiblioteca.model.Prestamo;

public interface IPrestamoService extends CrudRepository<Prestamo, Integer> {

}
