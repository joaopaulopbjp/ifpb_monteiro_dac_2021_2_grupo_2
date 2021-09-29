package com.dac.ecommerce.livros.model;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ESTOQUE")
public class Estoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "estoque_id")
	private Integer estoqueID;
	
	//estoque atual
	@OneToMany
	private List<PedidoItem> itensNoEstoque;
	
	//exluidos do estoque
	@OneToMany
	private List<PedidoItem> itensExcluidos;
	
	public Integer getEstoqueID() {
		return estoqueID;
	}

	public List<PedidoItem> getItensNoEstoque() {
		return itensNoEstoque;
	}

	public void setItensNoEstoque(List<PedidoItem> itensNoEstoque) {
		this.itensNoEstoque = itensNoEstoque;
	}

	public List<PedidoItem> getItensExcluidos() {
		return itensExcluidos;
	}

	public void setItensExcluidos(List<PedidoItem> itensExcluidos) {
		this.itensExcluidos = itensExcluidos;
	}

}
