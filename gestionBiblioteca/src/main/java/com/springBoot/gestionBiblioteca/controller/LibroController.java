package com.springBoot.gestionBiblioteca.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.model.Multa;
import com.springBoot.gestionBiblioteca.model.Prestamo;
import com.springBoot.gestionBiblioteca.model.Usuario;
import com.springBoot.gestionBiblioteca.service.ILibroService;
import com.springBoot.gestionBiblioteca.service.IMultaService;
import com.springBoot.gestionBiblioteca.service.IPrestamoService;
import com.springBoot.gestionBiblioteca.service.IStatusService;
import com.springBoot.gestionBiblioteca.service.IUsuarioService;
import com.springBoot.gestionBiblioteca.util.FieldErrorMessage;

import jakarta.validation.Valid;

@RestController
public class LibroController {
	@Autowired
	private ILibroService libroservice;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IMultaService multaService;
	
	@Autowired
	private IPrestamoService prestamoService;
	
	private static final int STATUS_DISPONIBLE = 1;
	private static final int STATUS_PRESTADO = 2;
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
		List<FieldErrorMessage>exceptionHandler(MethodArgumentNotValidException e){
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = fieldErrors.stream().map(fieldError -> 
		new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
		.collect(Collectors.toList());
		return fieldErrorMessages;
	}
	
	@GetMapping("/libros")
	Iterable<Libro> mostrar(){
		List<Libro> librosDisponibles = new ArrayList<>();
		libroservice.findAll().forEach(libro ->{
			if(libro.getStatus().getId() == STATUS_DISPONIBLE) {
				librosDisponibles.add(libro);
			}
		});
		return librosDisponibles;
	}
	
	@GetMapping("/libros/pretados/{documento}")
	Iterable<Prestamo> librosPrestados(@PathVariable String documento){
		List<Prestamo> librosPrestados = new ArrayList<>();
		prestamoService.findAll().forEach(prestamo ->{
			if(prestamo.getUsuario().getDocumento().equals(documento)) {
				prestamo.existeMulta(prestamo.getFechaVencimineto(), prestamo.getMulta());
				librosPrestados.add(prestamo);
			}
		});
		return librosPrestados;
	}
	
	@PutMapping("/libros/prestamo/{documento}/{id}")
	Optional<Libro> Prestamolibro(@PathVariable String documento,@PathVariable int id) {
		Libro libro = libroservice.findById(id).map(lb->{
			Libro g = new Libro();
			g.setId(lb.getId());
			g.setNombre(lb.getNombre());
			g.setStatus(statusService.findById(STATUS_PRESTADO).get());
			return libroservice.save(g);
		}).get();
		registro(documento,libro);
		return Optional.of(libro);
	}
	
	@PutMapping("/libro/devolucion/{documento}/{id}")
	Optional<Libro> Devolucionlibro(@PathVariable int id,@PathVariable String documento) {
		Libro libro = libroservice.findById(id).map(l->{
			Libro g = new Libro();
			g.setId(l.getId());
			g.setNombre(l.getNombre());
			g.setStatus(statusService.findById(STATUS_DISPONIBLE).get());
			return libroservice.save(g);
		}).get();
		devolvioLibro(libro);
		return Optional.of(libro);
	}
	
	@PostMapping("/usuario")
	Usuario crearUsuario(@Valid @RequestBody Usuario user) {
		return usuarioService.save(user);
	}
	
	@PostMapping("/libros/crear")
	Libro crearLibro(@Valid @RequestBody Libro libro) {
		libro.setStatus(statusService.findById(STATUS_DISPONIBLE).get());
		return libroservice.save(libro);
	}
	
	private void registro(String documento,Libro libro) {
		Multa multa = multa(documento);
		Prestamo prestamo = new Prestamo();
		prestamo.setLibro(libro);
		prestamo.setMulta(multa);
		prestamo.setUsuario(usuarioService.findById(documento).get());
		prestamoService.save(prestamo);
	}
	
	private Multa multa(String documento) {
		Multa multa = new Multa();
		multaService.save(multa);
		return multa;
	}
	
	public void devolvioLibro(Libro libro) {
		prestamoService.findAll().forEach(prestamo ->{
			if(prestamo.getLibro().getId() == libro.getId()) {
				prestamoService.delete(prestamo);
			}
		});
	}
}
