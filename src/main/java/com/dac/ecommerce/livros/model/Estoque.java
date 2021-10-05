package com.dac.ecommerce.livros.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_ESTOQUE")
public class Estoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "estoque_id")
	private Integer estoqueID;
	
	//itens atuais do estoque
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "estoque", fetch = FetchType.EAGER)
	private List<ItemEstoque> itensDoEstoque = new ArrayList<>();
	
	public Estoque() {}
	
	public void adicionarNoEstoque(ItemEstoque livro) {
		itensDoEstoque.add(livro);
	}

	@Override
	public String toString() {
		return "Estoque [estoqueID=" + estoqueID 
		+ ", itensDoEstoque=" + itensDoEstoque + "]";
	}
	
}
