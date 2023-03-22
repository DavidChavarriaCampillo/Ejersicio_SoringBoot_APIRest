package com.springBoot.gestionBiblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.model.Prestamo;

@Service
public interface PrestamoService extends BaseService<Prestamo, Integer> {
	public void registro(String documento,Libro libro) throws Exception;
	public List<Prestamo> prestamosUsuario(Integer id) throws Exception;
	public void eliminarPrestamo(Integer id) throws Exception;
}
