package com.dac.ecommerce.livros.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.repository.ItemEstoqueRepository;

@Service
public class ItemEstoqueService {

	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;
	
	@Autowired
	private LivroService livroService;
	
	public List<ItemEstoque> bucarTodosOsItensDoEstoque(){
		List<ItemEstoque> itens = itemEstoqueRepository.findAll();
		return itens;
	}
	
	public void salvarItem(ItemEstoque itemEstoque) {
		itemEstoqueRepository.save(itemEstoque);
	}
	
	public ItemEstoque bucarItem(Long id) {
		ItemEstoque itemEstoque = itemEstoqueRepository.findById(id).get();
		return itemEstoque;
	}
	
	public void adicionarItem(Integer qtd, Long id) {
		ItemEstoque itemEstoque = itemEstoqueRepository.findById(id).get();
		Integer qtdAtual = itemEstoque.getQuantidade() + qtd;
		itemEstoque.setQuantidade(qtdAtual);
		itemEstoqueRepository.save(itemEstoque);
	}
	
	public void removerItem(Integer qtd, Long id) {
		ItemEstoque itemEstoque = itemEstoqueRepository.findById(id).get();
		Integer qtdAtual = itemEstoque.getQuantidade() - qtd;
		itemEstoque.setQuantidade(qtdAtual);
		itemEstoqueRepository.save(itemEstoque);
	}
	
	public void deletar(Long id) {
		
		Long idLivro = itemEstoqueRepository.findById(id).get().getProduto().getId();
		Livro livro = livroService.buscarLivro(idLivro);
		livro.setAdicionadoEmEstoque(false);
		livroService.alterarLivro(livro, idLivro);
		itemEstoqueRepository.deletarItem(id);
	}
	
	public ItemEstoque buscarItemPeloLivro(Livro livro) {
		ItemEstoque itemEstoque= itemEstoqueRepository.findByProduto(livro);
		return itemEstoque;
	}
	
	public void alterar(ItemEstoque itemEstoque, Long id) {
		ItemEstoque bucarItem = itemEstoqueRepository.findById(id).get();
		BeanUtils.copyProperties(itemEstoque, bucarItem);
		itemEstoqueRepository.save(bucarItem);

	}
}
