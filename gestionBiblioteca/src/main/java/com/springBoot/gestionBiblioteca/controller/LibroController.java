package com.springBoot.gestionBiblioteca.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.service.LibroServiceImp;

@RestController
@RequestMapping("/api/v1/libros")
public class LibroController extends BaseControllerImp<Libro, LibroServiceImp>{
	
	@GetMapping("/disponibles")
	public ResponseEntity<?> disponibles(){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.disponibles());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"error, intentelo mas tarde\"}" + e.getMessage());
		}
	}
}
