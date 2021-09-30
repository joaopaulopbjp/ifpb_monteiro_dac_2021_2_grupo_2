package com.dac.ecommerce.livros.model.pedido;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_PEDIDO")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataCriacao;
	
	@Temporal(TemporalType.DATE)
	private Date dataFechamento;
	
	@Enumerated(EnumType.STRING)
	private PedidosStatus status;
	
	@OneToOne(fetch = FetchType.EAGER)
	private FormaPagamento formaPagamento;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pedido")
	private List<ItemPedido> itens;

	public Pedido() {
		this.status = PedidosStatus.NAO_FINALIZADO;
		this.dataCriacao = new Date();
		this.itens = new ArrayList<ItemPedido>();
	}
	
	public void adicionarItem(ItemPedido item) {
		this.itens.add(item);
	}
	

}
