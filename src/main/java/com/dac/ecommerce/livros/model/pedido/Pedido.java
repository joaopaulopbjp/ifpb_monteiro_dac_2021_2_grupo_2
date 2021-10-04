package com.dac.ecommerce.livros.model.pedido;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.joda.time.LocalDate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@Table(name = "TB_PEDIDO")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter(value = AccessLevel.NONE)
	@Temporal(TemporalType.DATE)
	private Date dataCriacao;
	
	@Setter(value = AccessLevel.NONE)
	@Temporal(TemporalType.DATE)
	private Date dataFechamento;
	
	@Enumerated(EnumType.STRING)
	private PedidoStatus status;
	
	private String motivoCancelamento;
	
	@OneToOne(fetch = FetchType.EAGER)
	private FormaPagamento formaPagamento;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pedido_fk")
	private List<ItemPedido> itens = new ArrayList<ItemPedido>();

	public Pedido() {
		this.status = PedidoStatus.NAO_FINALIZADO;
		
		// Setar data de criação e de fechamento
		this.dataCriacao = new Date();
		this.dataFechamento = LocalDate.fromDateFields(this.dataCriacao).plusWeeks(1).toDate();  // Adicionar 1 semanas na data de criação para obter o limite de cancelamento
	}
	
	public void adicionarItem(ItemPedido item) {
		this.itens.add(item);
	}
	
}