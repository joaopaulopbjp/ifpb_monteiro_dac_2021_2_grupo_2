package com.dac.ecommerce.livros.dto;

import com.dac.ecommerce.livros.model.livro.Editora;

import lombok.Data;

@Data
public class DTOEditora {
	
	private Long idEditora;
	private String cidadeEditora;
	private String nomeEditora;
	
	
	public Editora toEditora() {
		Editora editora = new Editora();
		editora.setCidade(this.cidadeEditora);
		editora.setNome(this.nomeEditora);
		return editora;
	}
	

}
