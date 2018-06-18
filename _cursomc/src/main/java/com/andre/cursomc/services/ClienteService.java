package com.andre.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.repositories.ClienteRepository;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository catrepo;
	
	public Cliente find(Integer id) {
		Cliente obj = catrepo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado ! Id : " + id
					+ ", Tipo : " + Cliente.class.getName()); 
		}
		return obj;
	}

}
