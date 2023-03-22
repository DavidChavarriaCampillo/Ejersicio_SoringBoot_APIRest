package com.springBoot.gestionBiblioteca.service;

import java.util.List;

import com.springBoot.gestionBiblioteca.model.Prestamo;
import com.springBoot.gestionBiblioteca.model.Usuario;

public interface UsuarioService extends BaseService<Usuario, Integer> {
	public Usuario prestarLibro(Integer id, String documento) throws Exception;
	public Usuario buscarConDocumento(String documento) throws Exception;
	public List<Prestamo> prestamos(Integer id) throws Exception;
	public Usuario devolverLibro(Integer id,String documento) throws Exception;
}
