package com.springBoot.gestionBiblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springBoot.gestionBiblioteca.service.BaseServiceImp;

public abstract class BaseControllerImp<E,S extends BaseServiceImp<E, Integer>> implements BaseController<E, Integer> {
	
	@Autowired
	protected S servicio;
	
	@GetMapping("")
	public ResponseEntity<?> getAll(){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.findAll());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"error, intentelo mas tarde\"}");
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Integer id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.findById(id));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"error, intentelo mas tarde\"}");
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> save(@RequestBody E entity){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(entity));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"error, intentelo mas tarde\"}");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody E entity){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id,entity));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"error, intentelo mas tarde\"}");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		try {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(servicio.delete(id));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"error, intentelo mas tarde\"}");
		}
	}
}
