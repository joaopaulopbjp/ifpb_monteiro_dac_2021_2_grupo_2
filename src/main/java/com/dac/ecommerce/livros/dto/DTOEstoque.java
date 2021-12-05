package com.dac.ecommerce.livros.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.dac.ecommerce.livros.model.estoque.Estoque;

import lombok.Data;

@Data
public class DTOEstoque {
	
	private Long idEstoque;
	
	@NotEmpty
	@Size(min=2, max=50)
	private String nomeEstoque;
	
	public Estoque toEstoque() {
		Estoque estoque = new Estoque();
		estoque.setNome(this.nomeEstoque);
		return estoque;
	}

}
