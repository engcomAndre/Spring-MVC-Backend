package com.andre.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Pedido;
import com.andre.cursomc.repositories.PedidoRepository;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository catrepo;
	
	public Pedido find(Integer id) {
		Pedido obj = catrepo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado ! Id : " + id
					+ ", Tipo : " + Pedido.class.getName()); 
		}
		return obj;
	}

}
