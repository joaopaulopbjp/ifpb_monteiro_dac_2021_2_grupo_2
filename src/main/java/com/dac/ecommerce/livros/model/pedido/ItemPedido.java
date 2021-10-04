package com.dac.ecommerce.livros.model.pedido;

import java.math.BigDecimal;

import javax.persistence.*;

import com.dac.ecommerce.livros.model.Livro;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_ITEM_PEDIDO")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "livro_fk")
	private Livro livro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pedido_fk", nullable = false)
	private Pedido pedido_fk;
	
	private Integer quantidade;
	
	private BigDecimal subtotal;
	
	private BigDecimal total;
	
	public ItemPedido() {}
	
	public ItemPedido(Livro livro, Integer quantidade) {
		this.livro = livro;
		this.quantidade = quantidade;
		this.subtotal = livro.getPreco();
		this.total = livro.getPreco().multiply(new BigDecimal(quantidade));
	}
		
}
