package com.dac.ecommerce.livros.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dac.ecommerce.livros.model.livro.Categoria;

import lombok.Data;

@Data
public class DTOCategoria {

	private Long idCategoria;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=50)
	private String nomeCategoria;
	
	public Categoria toCategoria() {
		Categoria categoria = new Categoria();
		categoria.setId(this.idCategoria);
		categoria.setNome(this.nomeCategoria);
		return categoria;
	}
	
}
