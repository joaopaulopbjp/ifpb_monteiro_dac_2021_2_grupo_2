package com.dac.ecommerce.livros.dto;

import lombok.Data;

@Data
public class DTOPedido {
	
	private Long idLivro;
	private Integer quantidade = 1;
	private String urlOrigem;
	
}
