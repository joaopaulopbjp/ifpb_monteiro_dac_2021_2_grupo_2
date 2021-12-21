package com.dac.ecommerce.livros.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_ENDERECO")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String cep;
	
	@Min(1)
	private Integer numero;

	@NotEmpty
	@Length(min=4, max=200)
	private String cidade;
	
	@NotEmpty
	@Length(max=2, min=2)
	private String estado;
	
	@NotEmpty
	@Length(min=4, max=200)
	private String bairro;
	
	@NotEmpty
	@Length(min=4, max=200)
	private String rua;
	
	@Column(nullable = true)
	private String complemento;
	
	@ManyToOne
	private Usuario usuario;
	
	public Endereco() {}

	public Endereco(String cep, Integer numero, String cidade, String estado,  String bairro,
			String rua, String complemento) {
		this.cep = cep;
		this.numero = numero;
		this.cidade = cidade;
		this.estado = estado;
		this.bairro = bairro;
		this.rua = rua;
		this.complemento = complemento;
	}

	@Override
	public String toString() {
		return "\nCEP: " + this.cep +
				"\nCidade: " + this.cidade + 
				"\nEstado: " + this.estado + 
				"\nBairro: " + this.bairro + 
				"\nRua: " + this.rua + 
				"\nComplemento: " + this.complemento +
				"\nNumero: Nº " + this.numero;
	}
	
	@Override
	public boolean equals(Object otherEndereco) {
		
		Endereco endereco = (Endereco) otherEndereco;
		
		if(
			endereco.getCep().equals(this.cep) && endereco.getNumero().compareTo(this.numero) == 0 &&
			endereco.getCidade().equals(this.cidade) && endereco.getEstado().equals(this.estado) &&
			endereco.getRua().equals(this.rua)
		  ) {
			return true;
		}
		
		return false;
	}

}
