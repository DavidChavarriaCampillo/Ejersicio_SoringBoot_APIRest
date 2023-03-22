package com.springBoot.gestionBiblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springBoot.gestionBiblioteca.model.Prestamo;

@Repository
public interface PrestamoRepository extends BaseRepository<Prestamo, Integer>{
	
	@Query(value = ("SELECT * FROM prestamo WHERE prestamo.usuario_id = :id"),nativeQuery = true)
	public List<Prestamo> prestamosUsuario(Integer id);
	
	@Query(value = ("SELECT * FROM prestamo WHERE prestamo.libro_id = :id"),nativeQuery = true)
	public Prestamo eliminarPrestamo(Integer id);
}
