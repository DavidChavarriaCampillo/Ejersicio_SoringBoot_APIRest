package com.springBoot.gestionBiblioteca.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springBoot.gestionBiblioteca.model.Usuario;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Integer>{
	
	@Query(value="SELECT * FROM usuario WHERE usuario.documento =:documento", nativeQuery = true)
	public Usuario buscarConDocumento(String documento); 
}
