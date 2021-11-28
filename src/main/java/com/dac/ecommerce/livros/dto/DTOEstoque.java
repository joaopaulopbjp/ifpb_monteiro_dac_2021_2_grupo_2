package com.dac.ecommerce.livros.dto;

import com.dac.ecommerce.livros.model.estoque.Estoque;

import lombok.Data;

@Data
public class DTOEstoque {
	
	private Long idEstoque;
	private String nomeEstoque;
	
	public Estoque toEstoque() {
		Estoque estoque = new Estoque();
		estoque.setNome(this.nomeEstoque);
		return estoque;
	}

}
