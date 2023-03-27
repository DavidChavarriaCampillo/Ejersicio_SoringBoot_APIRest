package com.springBoot.gestionBiblioteca.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.repository.StatusRepository;
import com.springBoot.gestionBiblioteca.service.LibroServiceImp;

@WebMvcTest(LibroController.class)
class LibroControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private StatusRepository statusRepository;
	
	@MockBean
	private LibroServiceImp libroServiceImp;
	
	List<Libro> lista = Arrays.asList(new Libro("onepiece"),new Libro("movi dick"),new Libro("pink panter"));
	List<Libro> listaLibros = new ArrayList<>();
	
	void setUp() {
		listaLibros = lista;
		
		listaLibros.get(0).setStatus(statusRepository.findById(1).get());
		listaLibros.get(1).setStatus(statusRepository.findById(1).get());
		listaLibros.get(2).setStatus(statusRepository.findById(1).get());
	}
	
	@Test
	void disponibles() throws Exception {
		when(libroServiceImp.disponibles()).thenReturn(lista);
		
		mvc.perform(get("/api/v1/libros/disponibles").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].nombre").value("onepiece"))
			.andExpect(jsonPath("$[1].nombre").value("movi dick"))
			.andExpect(jsonPath("$[2].nombre").value("pink panter"))
			.andExpect(jsonPath("$[0].status.id").value("1"))
			.andExpect(jsonPath("$[1].status.id").value(1))
			.andExpect(jsonPath("$[2].status.id").value(1));
	}
}
