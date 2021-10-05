package com.dac.ecommerce.livros.model.pedido;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Endereco {
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

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
