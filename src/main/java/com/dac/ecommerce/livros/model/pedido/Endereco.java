package com.dac.ecommerce.livros.model.pedido;

import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Endereco {
	
	private Integer cep;
	
	private Integer numero;
	
	private String cidade;
	
	private String estado;
	
	private String bairro;
	
	private String rua;
	
	private String complemento;
	
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
