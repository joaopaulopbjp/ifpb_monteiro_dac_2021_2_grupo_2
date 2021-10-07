package com.dac.ecommerce.livros.model.pedido;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.dac.ecommerce.livros.model.user.Endereco;
import com.dac.ecommerce.livros.model.user.Usuario;

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
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cliente_fk")
	private Usuario cliente;
	
	@Setter(value = AccessLevel.NONE)
	@Temporal(TemporalType.DATE)
	private Date dataCriacao;
	
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
	}
	
	public void adicionarItem(ItemPedido item) {
		this.itens.add(item);
	}
	
	public String toString() {
		String info = "\nID: " + this.id + "\nData de Criação: " + this.dataCriacao + "\nData de Fechamento: " + this.dataFechamento + 
				"\nStatus; " + this.status;
		
		if(this.status == PedidoStatus.CANCELADO) {
			info += "\nMotivo do Cancelamento: " + this.motivoCancelamento;
		}
		
		info += "\nEndereço de Entrega: " + this.enderecoEntrega.toString() + "\nForma de Pagamento: " + this.formaPagamento.toString();
		
		return info;
	}
	
}