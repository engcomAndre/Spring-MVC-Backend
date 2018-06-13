package com.aj.cursomc.services;

import javax.ejb.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aj.cursomc.domain.Categoria;
import com.aj.cursomc.repositories.CategoriaRepository;

import javassist.NotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) throws ObjectNotFoundException {
		Categoria categoria = repo.findOne(id);
		if (categoria == null) {
			throw new ObjectNotFoundException("Categoria não encontrada");
		}
		return categoria;
	}
}
