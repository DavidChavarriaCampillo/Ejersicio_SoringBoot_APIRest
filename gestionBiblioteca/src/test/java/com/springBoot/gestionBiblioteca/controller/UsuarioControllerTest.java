package com.springBoot.gestionBiblioteca.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.model.Multa;
import com.springBoot.gestionBiblioteca.model.Prestamo;
import com.springBoot.gestionBiblioteca.model.Usuario;
import com.springBoot.gestionBiblioteca.repository.LibroRepository;
import com.springBoot.gestionBiblioteca.service.LibroServiceImp;
import com.springBoot.gestionBiblioteca.service.PrestamoServiceImp;
import com.springBoot.gestionBiblioteca.service.UsuarioServiceImp;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	/*@Autowired
	private StatusRepository statusRepository;*/
	
	@MockBean
	private LibroRepository libroRepository;
	
	@MockBean
	private UsuarioServiceImp usuarioServiceImp;
	
	@MockBean
	private LibroServiceImp libroServiceImp;
	
	@MockBean
	private PrestamoServiceImp prestamoServiceImp;
	
	List<Libro> lista = Arrays.asList(new Libro("onepiece"),new Libro("movi dick"),new Libro("pink panter"));
	List<Libro> listaLibros = new ArrayList<>();
	
	List<Usuario> listaU = Arrays.asList(new Usuario("123","Juan"),new Usuario("456","David"));
	List<Usuario> listaUsuarios = new ArrayList<>();
	
	List<Prestamo> listaP = Arrays.asList(new Prestamo());
	List<Prestamo> listaPrestamos = new ArrayList<>();
	
	@BeforeEach
	void setUp() {
		listaLibros = lista;
		listaUsuarios = listaU;
		listaPrestamos = listaP;
		
		listaPrestamos.get(0).setLibro(listaLibros.get(0));
		listaPrestamos.get(0).setUsuario(listaUsuarios.get(0));
		listaPrestamos.get(0).setMulta(new Multa());
		
		/*listaLibros.get(0).setStatus(statusRepository.findById(1).get());
		listaLibros.get(1).setStatus(statusRepository.findById(1).get());
		listaLibros.get(2).setStatus(statusRepository.findById(1).get());*/
	}
	
	@Test
	void testPrestarLibro() throws Exception {
		when(usuarioServiceImp.prestarLibro(0,"123")).thenReturn(listaUsuarios.get(0));
		//when(libroServiceImp.estado(0)).thenReturn(listaLibros.get(0));
		/*when(libroRepository.existsById(0)).thenReturn(true);
		when(libroRepository.findById(0)).thenReturn(Optional.ofNullable(listaLibros.get(0)));
		when(libroRepository.save(listaLibros.get(0))).thenReturn(listaLibros.get(0));
		when(usuarioServiceImp.buscarConDocumento(anyString())).thenReturn(listaUsuarios.get(0));*/
		//when(prestamoRepository.save()).thenReturn(listaUsuarios.get(0));
		
		mvc.perform(put("/api/v1/usuario/prestar_libro/123/0").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			//.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			//.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.nombre").value("Juan"))
			.andExpect(jsonPath("$.documento").value("123"));
			//.andExpect(jsonPath("$.prestamo.libro.id").value("0"));
		
	}
	
	@Test
	void testPrestamos() throws Exception {
		when(usuarioServiceImp.prestamos(0)).thenReturn(listaPrestamos);
		
		mvc.perform(get("/api/v1/usuario/prestamos/0").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").exists())
			.andExpect(jsonPath("$[0].fechaPrestamo").exists())
			.andExpect(jsonPath("$[0].fechaVencimineto").exists())
			.andExpect(jsonPath("$[0].usuario").exists())
			.andExpect(jsonPath("$[0].libro").exists())
			.andExpect(jsonPath("$[0].multa").exists());	
	}
	
	@Test
	void testDevolverLibro() throws Exception {
		listaUsuarios.get(0).setPrestamo(listaPrestamos.get(0));
		when(usuarioServiceImp.devolverLibro(0,"123")).thenReturn(listaUsuarios.get(0));
		
		when(libroServiceImp.estado(0)).thenReturn(listaLibros.get(0));
		when(libroRepository.existsById(0)).thenReturn(true);
		when(libroRepository.findById(0)).thenReturn(Optional.ofNullable(listaLibros.get(0)));
		when(libroRepository.save(listaLibros.get(0))).thenReturn(listaLibros.get(0));
		when(usuarioServiceImp.buscarConDocumento(anyString())).thenReturn(listaUsuarios.get(0));

		//when(prestamoServiceImp.eliminarPrestamo(0)).thenReturn(listaPrestamos.get(0));
		
		mvc.perform(put("/api/v1/usuario/devolver_libro/123/0").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nombre").value("Juan"))
			.andExpect(jsonPath("$.documento").value("123"))
			.andExpect(jsonPath("$.prestamos").isEmpty());
	}
}
