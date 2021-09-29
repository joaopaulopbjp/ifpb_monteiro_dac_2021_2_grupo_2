package com.dac.ecommerce.livros.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PEDIDO_ITEM")
public class PedidoItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer quantidade;
	
	@ManyToOne
	@JoinColumn(name = "pedidoId")
	private Livro produto;

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Livro getProduto() {
		return produto;
	}

	public void setProduto(Livro produto) {
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

}
