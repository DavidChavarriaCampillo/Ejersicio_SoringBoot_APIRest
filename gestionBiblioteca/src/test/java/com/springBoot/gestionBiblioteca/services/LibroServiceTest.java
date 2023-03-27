package com.springBoot.gestionBiblioteca.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.repository.LibroRepository;
import com.springBoot.gestionBiblioteca.repository.StatusRepository;
import com.springBoot.gestionBiblioteca.service.LibroService;

@SpringBootTest
class LibroServiceTest {
	
	@MockBean
	LibroRepository libroRepository;
	
	@Autowired
	StatusRepository statusRepository;
	
	@Autowired
	LibroService libroService;
	
	List<Libro> lista = Arrays.asList(new Libro("onepiece"),new Libro("movi dick"),new Libro("pink panter"));
	List<Libro> listaLibros = new ArrayList<>();
	Libro libro;
	
	@BeforeEach
	void setUp() {
		listaLibros = lista;
		
		listaLibros.get(0).setId(0);
		listaLibros.get(0).setStatus(statusRepository.findById(1).get());
		
		listaLibros.get(1).setId(1);
		listaLibros.get(1).setStatus(statusRepository.findById(1).get());
		
		listaLibros.get(2).setId(2);
		listaLibros.get(2).setStatus(statusRepository.findById(1).get());
		
		libro = new Libro("hora de aventura");
	}
	
	@Test
	void testFindAll() throws Exception {
		when(libroService.findAll()).thenReturn(listaLibros);		
		
		List<Libro> libros = libroService.findAll();
		
		assertNotNull(libros.get(0).getId());
		assertNotNull(libros.get(0).getNombre());
		assertNotNull(libros.get(0).getStatus());
		
		assertNotNull(libros.get(1).getId());
		assertNotNull(libros.get(1).getNombre());
		assertNotNull(libros.get(1).getStatus());
		
		assertNotNull(libros.get(2).getId());
		assertNotNull(libros.get(2).getNombre());
		assertNotNull(libros.get(2).getStatus());		
	}
	
	@Test
	void testFindById() throws Exception {
		when(libroRepository.findById(0)).thenReturn(Optional.ofNullable(listaLibros.get(0)));
		when(libroRepository.findById(1)).thenReturn(Optional.ofNullable(listaLibros.get(1)));
		when(libroRepository.findById(2)).thenReturn(Optional.ofNullable(listaLibros.get(2)));
		
		Libro libro0 = libroService.findById(0);		
		
		assertNotNull(libro0.getId());
		assertNotNull(libro0.getNombre());
		assertNotNull(libro0.getStatus());
		assertSame(0, libro0.getId());
		assertSame("onepiece", libro0.getNombre());
		assertSame(1, libro0.getStatus().getId());
		assertSame("Disponible", libro0.getStatus().getStatus());
		
		Libro libro1 = libroService.findById(1);
		
		assertNotNull(libro1.getId());
		assertNotNull(libro1.getNombre());
		assertNotNull(libro1.getStatus());
		assertSame(1, libro1.getId());
		assertSame("movi dick", libro1.getNombre());
		assertSame(1, libro0.getStatus().getId());
		assertSame("Disponible", libro0.getStatus().getStatus());
		
		Libro libro2 = libroService.findById(2);
		
		assertNotNull(libro2.getId());
		assertNotNull(libro2.getNombre());
		assertNotNull(libro2.getStatus());
		assertSame(2, libro2.getId());
		assertSame("pink panter", libro2.getNombre());
		assertSame(1, libro0.getStatus().getId());
		assertSame("Disponible", libro0.getStatus().getStatus());
	}
	
	@Test	
	void testSave() throws Exception {
		when(libroService.save(libro)).thenReturn(libro);
		
		Libro l = libroService.save(libro);
		
		assertNotNull(l.getId());
		assertNotNull(l.getNombre());
		assertNotNull(l.getStatus());
		
		verify(libroRepository,times(1)).save(any());
	}
	
	@Test
	@Disabled
	void testUpdate() throws Exception {
		when(libroRepository.findById(0)).thenReturn(Optional.ofNullable(listaLibros.get(0)));
		when(libroRepository.save(listaLibros.get(0))).thenReturn(listaLibros.get(0));
		
		Libro libro0 = libroService.findById(0);
		
		assertNotNull(libro0.getId());
		assertNotNull(libro0.getNombre());
		assertNotNull(libro0.getStatus());
		assertSame(0, libro0.getId());
		assertSame("onepiece", libro0.getNombre());
		assertSame(1, libro0.getStatus().getId());
		assertSame("Disponible", libro0.getStatus().getStatus());
		
		Libro l = libroService.update(0, libro);
		
		assertNotNull(l.getId());
		assertNotNull(l.getNombre());
		assertSame(0, l.getId());
		assertSame("hora de aventura", l.getNombre());
		
		libro0 = libroService.findById(0);
		
		assertNotNull(libro0.getId());
		assertNotNull(libro0.getNombre());
		assertSame(0, libro0.getId());
		assertSame("hora de aventura", libro0.getNombre());
		assertSame(1, libro0.getStatus().getId());
		assertSame("Disponible", libro0.getStatus().getStatus());
	}
	
	@Test	
	void testDisponibles() throws Exception {
		when(libroService.disponibles()).thenReturn(listaLibros);		
		
		List<Libro> libros = libroService.disponibles();
		
		assertNotNull(libros.get(0).getId());
		assertNotNull(libros.get(0).getNombre());
		assertNotNull(libros.get(0).getStatus());
		assertSame(1, libros.get(0).getStatus().getId());
		
		assertNotNull(libros.get(1).getId());
		assertNotNull(libros.get(1).getNombre());
		assertNotNull(libros.get(1).getStatus());
		assertSame(1, libros.get(1).getStatus().getId());
		
		assertNotNull(libros.get(2).getId());
		assertNotNull(libros.get(2).getNombre());
		assertNotNull(libros.get(2).getStatus());
		assertSame(1, libros.get(2).getStatus().getId());
	}
	
	@Test	
	void testEstado() throws Exception {
		when(libroRepository.existsById(0)).thenReturn(true);
		when(libroRepository.findById(0)).thenReturn(Optional.ofNullable(listaLibros.get(0)));
		when(libroRepository.save(listaLibros.get(0))).thenReturn(listaLibros.get(0));
		
		Libro libro = libroService.estado(0);
		
		assertNotNull(libro.getId());
		assertNotNull(libro.getNombre());
		assertNotNull(libro.getStatus());
		assertSame(1, libro.getStatus().getId());		
	}
}
