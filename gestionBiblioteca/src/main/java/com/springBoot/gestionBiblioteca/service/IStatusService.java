package com.springBoot.gestionBiblioteca.service;

import org.springframework.data.repository.CrudRepository;

import com.springBoot.gestionBiblioteca.model.Status;

public interface IStatusService extends CrudRepository<Status, Integer> {

}
