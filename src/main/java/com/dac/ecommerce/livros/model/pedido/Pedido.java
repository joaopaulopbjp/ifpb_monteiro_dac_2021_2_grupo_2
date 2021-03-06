package com.dac.ecommerce.livros.model.pedido;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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
	
	@Length(max=200)
	private String motivoCancelamento;
	
	@OneToOne(fetch = FetchType.EAGER)
	private FormaPagamento formaPagamento;
	
	@NotNull(message = "{enderecoNaoAtributo}")
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
	
	public void removerItem(ItemPedido item) {
		this.itens.remove(item);
	}
	
	public String toString() {
		String info = "\n- Inforamções do Pedido" +
					  "\nID: " + this.id +
					  "\nData de Criação: " + this.dataCriacao +
					  "\nData de Fechamento: " + this.dataFechamento + 
					  "\nStatus; " + this.status;
		
		if(this.status == PedidoStatus.CANCELADO) {
			info += "\nMotivo do Cancelamento: " + this.motivoCancelamento;
		}
		
		info += "\n\n- Endereço de Entrega" + this.enderecoEntrega.toString() + "\n\n- Forma de Pagamento: " + this.formaPagamento.toString();
		
		return info;
	}
	
}