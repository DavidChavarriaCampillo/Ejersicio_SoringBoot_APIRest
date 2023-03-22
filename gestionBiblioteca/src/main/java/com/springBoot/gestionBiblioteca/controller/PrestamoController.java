package com.springBoot.gestionBiblioteca.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.gestionBiblioteca.model.Prestamo;
import com.springBoot.gestionBiblioteca.service.PrestamoServiceImp;

@RestController
@RequestMapping("/api/v1/prestamos")
public class PrestamoController extends BaseControllerImp<Prestamo, PrestamoServiceImp> {
	
}
