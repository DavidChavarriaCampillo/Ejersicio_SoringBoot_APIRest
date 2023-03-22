package com.springBoot.gestionBiblioteca.service;

import java.util.List;
import java.util.Optional;

import com.springBoot.gestionBiblioteca.repository.BaseRepository;

import jakarta.transaction.Transactional;

public abstract class BaseServiceImp<E,ID> implements BaseService<E,ID> {
	
	protected BaseRepository<E, ID> baseRepository;
	
	public BaseServiceImp(BaseRepository<E, ID> baseRepository){
        this.baseRepository = baseRepository;
    }
	
	@Override
	@Transactional
	public List<E> findAll() throws Exception {
		try {
			List<E> entitys = baseRepository.findAll();
			return entitys;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public E findById(ID id) throws Exception {
		try {
			Optional<E> entityOptional = baseRepository.findById(id);
			return entityOptional.get();
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public E save(E entity) throws Exception {
		try {
			E entiti = baseRepository.save(entity);
			return entiti;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public E update(ID id, E entity) throws Exception {
		try {
			Optional<E> entityOptional = baseRepository.findById(id);
			E usuario = entityOptional.get();
			usuario = baseRepository.save(entity);
			return usuario;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public boolean delete(ID id) throws Exception {
		try {
			if(baseRepository.existsById(id)) {
				baseRepository.deleteById(id);
				return true;
			}else {
				throw new Exception();
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
