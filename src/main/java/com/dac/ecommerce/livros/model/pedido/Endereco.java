package com.dac.ecommerce.livros.model.pedido;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Endereco {
	
	private String cep;
	
	private Integer numero;
	
	private String cidade;
	
	private String estado;
	
	private String bairro;
	
	private String rua;
	
	@Column(nullable = true)
	private String complemento;
	
	public Endereco(String cep, Integer numero, String cidade, String estado, String bairro, String rua, String complemento) {
		this.cep = cep;
		this.rua = rua;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.bairro = bairro;
		this.complemento = complemento;
	}
	
	public Endereco() {}
	
	public String toString() {
		return "\nCEP: " + this.cep +
				"\nCidade: " + this.cidade + 
				"\nEstado: " + this.estado + 
				"\nBairro: " + this.bairro + 
				"\nRua: " + this.rua + 
				"\nComplemento: " + this.complemento +
				"\nNumero: Nº " + this.numero;
	}
	

}
