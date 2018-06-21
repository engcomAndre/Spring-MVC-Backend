package com.andre.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.andre.cursomc.domain.Cidade;
import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.domain.Endereco;
import com.andre.cursomc.domain.enums.TipoCliente;
import com.andre.cursomc.dto.ClienteDTO;
import com.andre.cursomc.dto.ClienteNewDTO;
import com.andre.cursomc.repositories.CidadeRepository;
import com.andre.cursomc.repositories.ClienteRepository;
import com.andre.cursomc.repositories.EnderecoRepository;
import com.andre.cursomc.services.exception.DataIntegrityException;
import com.andre.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
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
		obj = clienteRepository.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;				
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
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos relacionados");
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
	
	public Cliente fromDTO(ClienteNewDTO objNewDTO) {
		Cliente cliente = new Cliente(null,
									  objNewDTO.getNome(),
									  objNewDTO.getEmail(), 
									  objNewDTO.getCpfOuCnpj(), 
									  TipoCliente.toEnum(objNewDTO.getTipoCliente()));
		
		/*...Another Option...*/
		/*Cidade cidade = new Cidade(objNewDTO.getCidadeId(),null,null);*/
		
		Cidade cidade = cidadeRepository.findOne(objNewDTO.getCidadeId());
		
		Endereco endereco = new Endereco(null,
										 objNewDTO.getLogradouro(), 
										 objNewDTO.getNumero(), 
										 objNewDTO.getComplemento(), 
										 objNewDTO.getBairro(), 
										 objNewDTO.getCep(),
										 cliente, 
										 cidade);
		
		cliente.getEnderecos().add(endereco);
		
		cliente.getTelefones().add(objNewDTO.getTelefone1());
		
		if(objNewDTO.getTelefone2() != null){
			cliente.getTelefones().add(objNewDTO.getTelefone2());			
		}
		
		if(objNewDTO.getTelefone3() != null){
			cliente.getTelefones().add(objNewDTO.getTelefone3());			
		}		
		
		return cliente;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());	
		newObj.setEmail(obj.getEmail());
	}

}
