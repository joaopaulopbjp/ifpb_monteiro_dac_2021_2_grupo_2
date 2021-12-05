package com.dac.ecommerce.livros.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dac.ecommerce.livros.model.livro.Editora;

import lombok.Data;

@Data
public class DTOEditora {
	
	private Long idEditora;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=50)
	private String cidadeEditora;
	
	@NotNull
	@NotEmpty
	@Size(min=2, max=50)
	private String nomeEditora;
	
	
	public Editora toEditora() {
		Editora editora = new Editora();
		editora.setId(this.idEditora);
		editora.setCidade(this.cidadeEditora);
		editora.setNome(this.nomeEditora);
		return editora;
	}
	

}
