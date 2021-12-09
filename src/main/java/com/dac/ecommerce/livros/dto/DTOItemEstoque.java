package com.dac.ecommerce.livros.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import com.dac.ecommerce.livros.model.estoque.Estoque;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import lombok.Data;

@Data
public class DTOItemEstoque {
	
	
	private Long id;
	
	@NotNull
	private BigDecimal precoItem;
	@Positive
	@NotNull
	private Integer qtd;
	@NotNull
	private Estoque estoque;
	@Positive
	@NotNull
	private Long idlivro;
	
	public ItemEstoque toItemEstoque() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setId(this.id);
		itemEstoque.setPreco(this.precoItem);
		itemEstoque.setQuantidade(this.qtd);
		itemEstoque.setEstoque(this.estoque);
		return itemEstoque;
	}
}
