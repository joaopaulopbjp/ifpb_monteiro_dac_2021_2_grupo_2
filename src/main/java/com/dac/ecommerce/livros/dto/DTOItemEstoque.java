package com.dac.ecommerce.livros.dto;

import java.math.BigDecimal;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import lombok.Data;

@Data
public class DTOItemEstoque {
	
	private Long idItem;
	private BigDecimal precoItem;
	private Integer qtd;
	private Long idEstoqueItem;
	private Long idLivroItem;
	
	public ItemEstoque toItemEstoque() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setId(this.idEstoqueItem);
		itemEstoque.setPreco(this.precoItem);
		itemEstoque.setQuantidade(this.qtd);
		return itemEstoque;
	}
}
