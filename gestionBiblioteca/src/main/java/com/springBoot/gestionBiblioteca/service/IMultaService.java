package com.springBoot.gestionBiblioteca.service;

import org.springframework.data.repository.CrudRepository;

import com.springBoot.gestionBiblioteca.model.Multa;

public interface IMultaService extends CrudRepository<Multa, Integer> {

}
