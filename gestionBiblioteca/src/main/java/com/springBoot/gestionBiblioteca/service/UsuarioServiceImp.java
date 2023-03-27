package com.springBoot.gestionBiblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.model.Prestamo;
import com.springBoot.gestionBiblioteca.model.Usuario;
import com.springBoot.gestionBiblioteca.repository.BaseRepository;
import com.springBoot.gestionBiblioteca.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp extends BaseServiceImp<Usuario, Integer> implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private LibroService libroService;
	
	@Autowired
	private PrestamoService prestamoService;
	
	public UsuarioServiceImp(BaseRepository<Usuario, Integer> baseRepository) {
        super(baseRepository);
    }
	
	public Usuario prestarLibro(Integer id,String documento) throws Exception {
		try {
			Libro libro = libroService.estado(id);
			prestamoService.registro(documento, libro);
			return usuarioRepository.buscarConDocumento(documento);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Usuario buscarConDocumento(String documento) throws Exception {
		try {
			return usuarioRepository.buscarConDocumento(documento);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Prestamo> prestamos(Integer id) throws Exception {
		try {
			return prestamoService.prestamosUsuario(id);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Usuario devolverLibro(Integer id,String documento) throws Exception {
		try {
			libroService.estado(id);
			prestamoService.eliminarPrestamo(id);
			return usuarioRepository.buscarConDocumento(documento);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
