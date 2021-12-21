package com.dac.ecommerce.livros.dto;

import com.dac.ecommerce.livros.model.user.Endereco;

import lombok.Data;

@Data
public class DTOEndereco {
	
	private Long id;
	private String cep;
	private Integer numero;
	private String cidade;
	private String estado;
	private String bairro;
	private String rua;
	private String complemento;
	
	public DTOEndereco(Endereco endereco) {
		this.id = endereco.getId();
		this.cep = endereco.getCep();
		this.numero = endereco.getNumero();
		this.cidade = endereco.getCidade();
		this.estado = endereco.getEstado();
		this.bairro = endereco.getBairro();
		this.rua = endereco.getRua();
		this.complemento = endereco.getComplemento();
	}

}
