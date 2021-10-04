package com.dac.ecommerce.livros.model;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_ITEM_ESTOQUE")
public class ItemEstoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itemId")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "livro_id")
	private Livro produto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "estoque_id")
	private Estoque estoque;
	
	private Integer quantidade;
	
	private BigDecimal preco;

	@Override
	public String toString() {
		return "ItemEstoque [id=" +
				id + ", quantidade=" + 
				quantidade + ", produto=" 
				+ produto + ", estoque=" 
				+ "]";
	}

}
