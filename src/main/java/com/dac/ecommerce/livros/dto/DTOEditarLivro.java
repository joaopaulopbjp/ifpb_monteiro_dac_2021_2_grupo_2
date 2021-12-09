package com.dac.ecommerce.livros.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class DTOEditarLivro {
	
	private Long id;
	@NotBlank
	@Size(min = 2, max = 50)
	private String titulo;
	@NotBlank
	@Size(min = 2, max = 150)
	private String Descricao;
	@NotBlank
	@Size(min = 1, max = 6)
	private String preco;
	

}
