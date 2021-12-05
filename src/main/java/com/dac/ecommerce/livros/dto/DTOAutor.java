package com.dac.ecommerce.livros.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dac.ecommerce.livros.model.livro.Autor;

import lombok.Data;

@Data
public class DTOAutor {
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=50)
	private String nomeAutor;
	private Long idAutor;
	

	
	public Autor toAutor() {
		Autor autor = new Autor();
		autor.setNome(this.nomeAutor);
		autor.setId(this.idAutor);
		return autor;
	}

}
