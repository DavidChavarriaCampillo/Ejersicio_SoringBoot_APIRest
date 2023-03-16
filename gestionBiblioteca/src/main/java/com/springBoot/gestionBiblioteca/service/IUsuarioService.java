package com.springBoot.gestionBiblioteca.service;

import org.springframework.data.repository.CrudRepository;

import com.springBoot.gestionBiblioteca.model.Usuario;

public interface IUsuarioService extends CrudRepository<Usuario, String> {

}
