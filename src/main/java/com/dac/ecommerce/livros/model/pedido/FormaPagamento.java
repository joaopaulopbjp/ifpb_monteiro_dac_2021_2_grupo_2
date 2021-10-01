package com.dac.ecommerce.livros.model.pedido;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_FORMAS_PAGAMENTO")
public class FormaPagamento {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	private String tipo;
	
	private Boolean isActive;
	
	public FormaPagamento(String tipo) {
		this.tipo = tipo;
		this.isActive = true;
	}
	
	public String toString() {
		return "ID: " + this.id + "Tipo: " + this.tipo + "Ativo: " + this.isActive;
	}
	
}
