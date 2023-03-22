package com.springBoot.gestionBiblioteca.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBoot.gestionBiblioteca.model.Libro;
import com.springBoot.gestionBiblioteca.model.Multa;
import com.springBoot.gestionBiblioteca.model.Prestamo;
import com.springBoot.gestionBiblioteca.repository.BaseRepository;
import com.springBoot.gestionBiblioteca.repository.MultaRepository;
import com.springBoot.gestionBiblioteca.repository.PrestamoRepository;
import com.springBoot.gestionBiblioteca.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class PrestamoServiceImp extends BaseServiceImp<Prestamo, Integer> implements PrestamoService {

	@Autowired
	private PrestamoRepository prestamoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MultaRepository multaRepository;
	
	public PrestamoServiceImp(BaseRepository<Prestamo, Integer> baseRepository) {
		super(baseRepository);
	}
	
	@Override
	@Transactional
	public void registro(String documento,Libro libro) throws Exception {
		try {
			Multa multa = new Multa();
			multaRepository.save(multa);
			Prestamo prestamo = new Prestamo();
			prestamo.setLibro(libro);
			prestamo.setMulta(multa);
			prestamo.setUsuario(usuarioRepository.buscarConDocumento(documento));
			prestamoRepository.save(prestamo);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public List<Prestamo> prestamosUsuario(Integer id) throws Exception{
		try {
			List<Prestamo> prestamos = new ArrayList<>();
			prestamoRepository.prestamosUsuario(id).forEach(p ->{
				p.existeMulta(p.getFechaVencimineto(), p.getMulta());
				prestamos.add(p);
			});
			return prestamos;		
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public void eliminarPrestamo(Integer id) throws Exception {
		try {
			Prestamo prestamo = prestamoRepository.eliminarPrestamo(id);
			prestamoRepository.delete(prestamo);
			multaRepository.deleteById(prestamo.getMulta().getId());
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
