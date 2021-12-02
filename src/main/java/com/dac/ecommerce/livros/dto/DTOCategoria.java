package com.dac.ecommerce.livros.dto;

import com.dac.ecommerce.livros.model.livro.Categoria;

import lombok.Data;

@Data
public class DTOCategoria {

	private Long idCategoria;
	private String nomeCategoria;
	
	public Categoria toCategoria() {
		Categoria categoria = new Categoria();
		categoria.setId(this.idCategoria);
		categoria.setNome(this.nomeCategoria);
		return categoria;
	}
	
}
