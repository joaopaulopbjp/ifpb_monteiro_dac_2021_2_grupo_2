package com.dac.ecommerce.livros.services;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.Estoque;
import com.dac.ecommerce.livros.model.ItemEstoque;
import com.dac.ecommerce.livros.repository.EstoqueRepository;
import com.dac.ecommerce.livros.repository.ItemEstoqueRepository;

@Service
public class EstoqueService {
	
	@Autowired
	private EstoqueRepository repository;
	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;
	
	//adicionar no estoque atual
	public String adicionarNoEstoque(ItemEstoque item){
		boolean verificarItem = verificaItemNoEstoque(item);
		if(verificarItem) {
			adicionarQtdEstoque(item);
			return "ITEM DO ESTOQUE ALTERADO COM SUCESSO!";
		}else {
			Estoque novoEstoque = new Estoque();
			novoEstoque.adicionarNoEstoque(item);
			BigDecimal preco = item.getProduto().getPreco();
			item.setPreco(preco);
			item.setEstoque(novoEstoque);
			repository.save(novoEstoque);
			return "ITEM ADICIONADO AO ESTOQUE COM SUCESSO!";
		}
	}
	
	public boolean verificaItemNoEstoque(ItemEstoque item) {
		List<ItemEstoque> itens = itemEstoqueRepository.findAll();
		for (ItemEstoque itemEstoque : itens) {
			if(itemEstoque.getProduto()
			.getId() == item.getProduto().getId()) { 
				return true;
			}
		}
		return false;
	}
	public String consultarLivrosMaisBaratosDoEstoque() throws Exception{
		Page<ItemEstoque> paginaItens = itemEstoqueRepository
		.findAll(PageRequest.of(0,5, Sort.by(Sort.Direction.ASC, "preco")));
		String itens = "";
		for (ItemEstoque itemEstoque : paginaItens) {
			itens += itemEstoque.toString();
		}
		if(itens.length() == 0) {
			throw new Exception("[ERROR] NÃO FOI POSSÍVEL BUSCAR OS LIVROS!");
		}
		return itens;
	}
	public void adicionarQtdEstoque(ItemEstoque item) {
		List<ItemEstoque> itens = itemEstoqueRepository.findAll();
		ItemEstoque iEstoque = null;
		for (ItemEstoque itemEstoque : itens) {
			if(itemEstoque.getProduto().getIsbn().
			equals(item.getProduto().getIsbn())) {
				iEstoque = itemEstoque;
			}
		}
		Integer qtdAtual = item.getQuantidade() + iEstoque.getQuantidade();
		iEstoque.setQuantidade(qtdAtual);
		alterarItemEstoque(iEstoque);
	}

	public void alterarItemEstoque(ItemEstoque item) {
		itemEstoqueRepository.save(item);
	}
}
