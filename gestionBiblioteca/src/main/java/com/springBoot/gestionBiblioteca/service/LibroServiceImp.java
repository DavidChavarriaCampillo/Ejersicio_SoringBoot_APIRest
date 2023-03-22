package com.springBoot.gestionBiblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.repository.BaseRepository;
import com.springBoot.gestionBiblioteca.repository.LibroRepository;
import com.springBoot.gestionBiblioteca.repository.StatusRepository;

import jakarta.transaction.Transactional;

@Service
public class LibroServiceImp extends BaseServiceImp<Libro, Integer> implements LibroService {
	
	@Autowired
	private LibroRepository libroRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	private static final int STATUS_DISPONIBLE = 1;
	private static final int STATUS_PRESTADO = 2;
	
	public LibroServiceImp(BaseRepository<Libro, Integer> baseRepository) {
        super(baseRepository);
    }

	@Override
	@Transactional
	public List<Libro> disponibles() throws Exception {
		try {
			return libroRepository.disponibles();
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public Libro estado(Integer id) throws Exception {
		try {
			Libro libroDevuelto = new Libro();
			if(libroRepository.existsById(id)) {
				Libro libroPrestado = libroRepository.findById(id).get();
				libroDevuelto.setId(libroPrestado.getId());
				libroDevuelto.setNombre(libroPrestado.getNombre());
				if(libroPrestado.getStatus().getId() == STATUS_PRESTADO) {
					libroDevuelto.setStatus(statusRepository.findById(STATUS_DISPONIBLE).get());
				}else {
					libroDevuelto.setStatus(statusRepository.findById(STATUS_PRESTADO).get());
				}			
				libroRepository.save(libroDevuelto);
			}
			return libroDevuelto;
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
