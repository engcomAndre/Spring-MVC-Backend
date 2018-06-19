package com.andre.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.dto.ClienteDTO;
import com.andre.cursomc.repositories.ClienteRepository;
import com.andre.cursomc.services.exception.DataIntegrityException;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id) {
		Cliente obj = clienteRepository.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado ! Id : " + id
					+ ", Tipo : " + Cliente.class.getName()); 
		}
		return obj;
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return clienteRepository.save(obj);				
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj,obj);
		return clienteRepository.save(newObj);
	}	
	
	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.delete(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
		}
	}	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();		
	}
	
	public Page<Cliente> findPage(Integer page,Integer linesPerPage,String orderBy,String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction),orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());	
		newObj.setEmail(obj.getEmail());
	}

}
