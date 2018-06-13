package com.andre.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Categoria;
import com.andre.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository catrepo;
	
	public Categoria buscar(Integer id) {
		Categoria obj = catrepo.findOne(id);
		return obj;
	}

}
