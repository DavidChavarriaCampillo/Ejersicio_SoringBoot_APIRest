package com.springBoot.gestionBiblioteca.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.model.Multa;
import com.springBoot.gestionBiblioteca.model.Prestamo;
import com.springBoot.gestionBiblioteca.model.Usuario;
import com.springBoot.gestionBiblioteca.repository.LibroRepository;
import com.springBoot.gestionBiblioteca.repository.PrestamoRepository;
import com.springBoot.gestionBiblioteca.repository.StatusRepository;
import com.springBoot.gestionBiblioteca.repository.UsuarioRepository;
import com.springBoot.gestionBiblioteca.service.UsuarioService;

@SpringBootTest
class UsuarioSeviceTest {
	
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@MockBean
	private LibroRepository libroRepository;
	
	@MockBean
	private PrestamoRepository prestamoRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	List<Libro> lista = Arrays.asList(new Libro("onepiece"),new Libro("movi dick"),new Libro("pink panter"));
	List<Libro> listaLibros = new ArrayList<>();
	
	List<Prestamo> listaP = Arrays.asList(new Prestamo(),new Prestamo());
	List<Prestamo> listaPrestamos = new ArrayList<>();
	
	List<Usuario> listaU = Arrays.asList(new Usuario("123","Juan"),new Usuario("456","David"));
	List<Usuario> listaUsuarios = new ArrayList<>();
	Libro libro;
	
	@BeforeEach
	void setUp() {
		
		listaLibros = lista;
		
		listaLibros.get(0).setStatus(statusRepository.findById(1).get());
		listaLibros.get(1).setStatus(statusRepository.findById(1).get());
		listaLibros.get(2).setStatus(statusRepository.findById(1).get());
		
		listaUsuarios = listaU;
		listaPrestamos = listaP;
		
		listaPrestamos.get(0).setUsuario(listaUsuarios.get(0));
		listaPrestamos.get(0).setLibro(listaLibros.get(0));
		listaPrestamos.get(0).setMulta(new Multa());
		
		listaPrestamos.get(1).setUsuario(listaUsuarios.get(0));
		listaPrestamos.get(1).setLibro(listaLibros.get(1));
		listaPrestamos.get(1).setMulta(new Multa());
		
		libro = new Libro("hora de aventura");
		
	}
	
	@Test
	void testPrestarLibro() throws Exception {
		when(usuarioRepository.buscarConDocumento(anyString())).thenReturn(listaUsuarios.get(0));
		when(libroRepository.existsById(0)).thenReturn(true);
		when(libroRepository.findById(0)).thenReturn(Optional.ofNullable(listaLibros.get(0)));
		when(libroRepository.save(listaLibros.get(0))).thenReturn(listaLibros.get(0));
		
		Usuario usuario = usuarioService.prestarLibro(0, "123");
		
		assertNotNull(usuario.getDocumento());
		assertNotNull(usuario.getNombre());
		assertNotNull(usuario.getPrestamos().get(0).getFechaPrestamo());
		assertNotNull(usuario.getPrestamos().get(0).getFechaVencimineto());
		assertNotNull(usuario.getPrestamos().get(0).getLibro());
		assertNotNull(usuario.getPrestamos().get(0).getMulta());
		assertNotNull(usuario.getPrestamos().get(0).getUsuario());
		assertSame(2, usuario.getPrestamos().get(0).getLibro().getStatus().getId());
		assertSame("onepiece", usuario.getPrestamos().get(0).getLibro().getNombre());
	}
	
	@Test
	void testBuscarConDocumento() throws Exception {
		listaUsuarios.get(0).setPrestamo(listaPrestamos.get(0));
		
		when(usuarioRepository.buscarConDocumento("123")).thenReturn(listaUsuarios.get(0));
		
		Usuario usuario = usuarioService.buscarConDocumento("123");
		
		assertNotNull(usuario.getDocumento());
		assertNotNull(usuario.getNombre());
		assertNotNull(usuario.getPrestamos().get(0).getFechaPrestamo());
		assertNotNull(usuario.getPrestamos().get(0).getFechaVencimineto());
		assertNotNull(usuario.getPrestamos().get(0).getLibro());
		assertNotNull(usuario.getPrestamos().get(0).getMulta());
		assertNotNull(usuario.getPrestamos().get(0).getUsuario());
		
	}
	
	@Test
	void testPrestamos() throws Exception {
		when(prestamoRepository.prestamosUsuario(1)).thenReturn(listaPrestamos);

		
		List<Prestamo> listaPrestamos = usuarioService.prestamos(1);
		
		assertNotNull(listaPrestamos.get(0).getFechaPrestamo());
		assertNotNull(listaPrestamos.get(0).getFechaVencimineto());
		assertNotNull(listaPrestamos.get(0).getLibro());
		assertNotNull(listaPrestamos.get(0).getMulta());
		assertNotNull(listaPrestamos.get(0).getUsuario());
		
		assertNotNull(listaPrestamos.get(1).getFechaPrestamo());
		assertNotNull(listaPrestamos.get(1).getFechaVencimineto());
		assertNotNull(listaPrestamos.get(1).getLibro());
		assertNotNull(listaPrestamos.get(1).getMulta());
		assertNotNull(listaPrestamos.get(1).getUsuario());
		
	}
	
	@Test
	void testDevolverLibro() throws Exception {
		listaUsuarios.get(0).setPrestamo(listaPrestamos.get(0));
		
		when(usuarioRepository.buscarConDocumento(anyString())).thenReturn(listaUsuarios.get(0));
		when(libroRepository.existsById(0)).thenReturn(true);
		when(libroRepository.findById(0)).thenReturn(Optional.ofNullable(listaLibros.get(0)));
		when(libroRepository.save(listaLibros.get(0))).thenReturn(listaLibros.get(0));
		when(prestamoRepository.eliminarPrestamo(0)).thenReturn(listaPrestamos.get(0));
		
		Usuario usuario = usuarioService.devolverLibro(0, "123");
		
		assertNull(usuario.getPrestamos().get(0));
		
	}
}
