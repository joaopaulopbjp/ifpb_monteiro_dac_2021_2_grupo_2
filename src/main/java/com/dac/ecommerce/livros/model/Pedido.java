package com.dac.ecommerce.livros.model;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PEDIDO")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pedidoId")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private PedidosStatus status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
	private List<PedidoItem> itens;

	public Pedido(List<PedidoItem> itens) {
		this.itens = itens;
		status = PedidosStatus.RECEBIDO;
	}

	public Pedido() {}

}
