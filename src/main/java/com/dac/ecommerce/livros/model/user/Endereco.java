package com.dac.ecommerce.livros.model.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Embeddable
public class Endereco {
	
	@NotEmpty
	private String cep = "";
	
	@Column(nullable = false)
	private Integer numero = 0;
	
	@NotEmpty
	@Length(min=4, max=200)
	private String cidade = "";
	
	@NotEmpty
	@Length(max=2, min=2, message = "{estado}")
	private String estado = "";
	
	@NotEmpty
	@Length(min=4, max=200)
	private String bairro = "";
	
	@NotEmpty
	@Length(min=4, max=200)
	private String rua = "";
	
	@Column(nullable = true)
	private String complemento = "";
	
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
