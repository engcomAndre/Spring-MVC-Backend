package com.andre.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.andre.cursomc.domain.Cliente;
import com.andre.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {
	 static final long serialVersionUID = 1L;
	 
	private Integer id;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Length(min=5,max=120,message="Tamanho do nome inválido")
	private String nome;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Email(message="Emial Inválido")
	private String email;	
	
	public ClienteDTO() {}
	
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
	}	

	public ClienteDTO(Integer id, String nome, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
