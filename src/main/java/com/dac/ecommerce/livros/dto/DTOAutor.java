package com.dac.ecommerce.livros.dto;

import com.dac.ecommerce.livros.model.livro.Autor;

import lombok.Data;

@Data
public class DTOAutor {
	
	private String nomeAutor;
	private Long idAutor;
	
	public Autor toAutor() {
		Autor autor = new Autor();
		autor.setNome(this.nomeAutor);
		autor.setId(this.idAutor);
		return autor;
	}

}
