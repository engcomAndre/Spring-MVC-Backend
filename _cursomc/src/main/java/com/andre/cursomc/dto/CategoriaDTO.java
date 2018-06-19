package com.andre.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.andre.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {
	 static final long serialVersionUID = 1L;
	private Integer id;
	
	@NotEmpty
	@Length(min=5,max=80,message="Tamanho do nome inválido ")
	private String nome;
	
	public CategoriaDTO() {}
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
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
	

}
