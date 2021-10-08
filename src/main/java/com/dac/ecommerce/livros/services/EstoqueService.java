package com.dac.ecommerce.livros.services;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.EstoqueException;
import com.dac.ecommerce.livros.exceptions.PaginaInvalidaException;
import com.dac.ecommerce.livros.model.estoque.Estoque;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.repository.EstoqueRepository;
import com.dac.ecommerce.livros.repository.ItemEstoqueRepository;
import com.dac.ecommerce.livros.repository.LivroRepository;

@Service
public class EstoqueService {
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	//adicionar no estoque atual
	public String adicionarNoEstoque(String isbn, Integer quantidade) throws Exception {
		Livro buscaLivro = livroRepository.findByIsbn(isbn);
		
		if(buscaLivro == null) {
			throw new Exception("[ERROR] NÃO FOI POSSÍVEL ADICIONAR ITEM NO ESTOUE");
		}
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setProduto(buscaLivro);
		itemEstoque.setQuantidade(quantidade);
		boolean verificarItem = verificaItemNoEstoque(itemEstoque);
		if(verificarItem) {
			adicionarQtdEstoque(itemEstoque);
			return "- ITEM DO ESTOQUE ALTERADO COM SUCESSO! -";
		}else {
			Estoque novoEstoque = new Estoque();
			novoEstoque.adicionarNoEstoque(itemEstoque);
			BigDecimal preco = itemEstoque.getProduto().getPreco();
			itemEstoque.setPreco(preco);
			itemEstoque.setEstoque(novoEstoque);
			estoqueRepository.save(novoEstoque);
			return "- ITEM ADICIONADO AO ESTOQUE COM SUCESSO! -";
		}
	}
	
	private boolean verificaItemNoEstoque(ItemEstoque item) {
		List<ItemEstoque> itens = itemEstoqueRepository.findAll();
		for (ItemEstoque itemEstoque : itens) {
			if(itemEstoque.getProduto()
			.getId() == item.getProduto().getId()) { 
				return true;
			}
		}
		return false;
	}

	public String consultarItensMaisBaratosDoEstoque() throws Exception{
		List<ItemEstoque> itensOrdenados = itemEstoqueRepository.ordenarItensDoEstoquePeloPreco();
		
		if(itensOrdenados.size() == 0) {
			throw new Exception("[ERROR] NÃO FOI POSSÍVEL BUSCAR OS LIVROS!");
		}
		String itens = "";
		List<ItemEstoque> itensDisponiveis = new ArrayList<>();
		for (ItemEstoque itemEstoque : itensOrdenados) {
			if(itemEstoque.getQuantidade() != 0) {
				itensDisponiveis.add(itemEstoque);
			}
		}
		if(itensDisponiveis.size() > 5) {
			for (int i = 0; i < 5; i++) {
				itens += itensDisponiveis.get(i).toString();
			}
		}else {
			for (ItemEstoque itemEstoque : itensDisponiveis) {
				itens += itemEstoque.toString();
			}
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

	public Integer consultarQuantidadeEmEstoque(Long idLivro) {
		Livro livro = livroRepository.findById(idLivro).get();
		ItemEstoque itemEstoque = itemEstoqueRepository.findByProduto(livro);
		return itemEstoque.getQuantidade();
	}
	
	public String listarLivrosPorPaginacao(Integer numeroPagina) throws PaginaInvalidaException {
		Pageable pageable = PageRequest.of((numeroPagina - 1), 5, Sort.by("titulo").ascending());
		
		Page<Livro> pagina = itemEstoqueRepository.consultarTodosLivrosPaginado(pageable);
		
		if(pagina.isEmpty()) {
			throw new PaginaInvalidaException();
		}
		
		String livros = "";
		for(Livro livro : pagina) {
			livros += livro.toString() + "\n";
		}
				
		return livros;
	}
	
	public void reduzirEstoque(Livro livro, int quantidade) throws EstoqueException {
		ItemEstoque itemEstoque = pesquisarItemEstoquePorLivro(livro);
		
		if(itemEstoque.getQuantidade() == 0) {
			throw new EstoqueException("[ERROR ESTOQUE] - ESTOQUE DO ITEM ESTÁ ZERADO!");
		}
		
		itemEstoque.setQuantidade(itemEstoque.getQuantidade() - quantidade);
		salvarItemEstoque(itemEstoque);
	}
	
	public void reporEstoquePedidoCancelado(Livro livro, int quantidade) {
		ItemEstoque itemEstoque = pesquisarItemEstoquePorLivro(livro);
		itemEstoque.setQuantidade(itemEstoque.getQuantidade() + quantidade);
		salvarItemEstoque(itemEstoque);
	}
	
	private void salvarItemEstoque(ItemEstoque itemEstoque) {
		itemEstoqueRepository.save(itemEstoque);
	}
	
	private ItemEstoque pesquisarItemEstoquePorLivro(Livro livro) {
		return itemEstoqueRepository.findByProduto(livro);
	}
}
