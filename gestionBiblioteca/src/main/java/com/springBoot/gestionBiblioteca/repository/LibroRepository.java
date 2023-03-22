package com.springBoot.gestionBiblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springBoot.gestionBiblioteca.model.Libro;

@Repository
public interface LibroRepository extends BaseRepository<Libro, Integer> {
	
	@Query(value= "SELECT * FROM libro WHERE libro.status_id = 1", nativeQuery = true)
	public List<Libro> disponibles(); 
}
