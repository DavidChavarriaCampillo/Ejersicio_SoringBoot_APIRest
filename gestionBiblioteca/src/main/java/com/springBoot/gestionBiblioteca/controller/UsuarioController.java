package com.springBoot.gestionBiblioteca.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.gestionBiblioteca.model.Usuario;
import com.springBoot.gestionBiblioteca.service.UsuarioServiceImp;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController extends BaseControllerImp<Usuario,UsuarioServiceImp> {
	
	@PutMapping("/prestar_libro/{documento}/{id}")
	public ResponseEntity<?> prestarLibro(@PathVariable Integer id,@PathVariable String documento) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.prestarLibro(id, documento));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"error, intentelo mas tarde\"}");
		}
	}
	
	@GetMapping("/prestamos/{id}")
	public ResponseEntity<?> prestamos(@PathVariable Integer id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.prestamos(id));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"error, intentelo mas tarde\"}");
		}
	}
	
	@PutMapping("/devolver_libro/{documento}/{id}")
	public ResponseEntity<?> devolverLibro(@PathVariable Integer id,@PathVariable String documento) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(servicio.devolverLibro(id, documento));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"error, intentelo mas tarde\"}" + e.getMessage());
		}
	}
}
