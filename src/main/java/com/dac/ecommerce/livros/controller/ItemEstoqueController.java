package com.dac.ecommerce.livros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dac.ecommerce.livros.dto.DTOItemEstoque;
import com.dac.ecommerce.livros.model.estoque.Estoque;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.services.EstoqueService;
import com.dac.ecommerce.livros.services.ItemEstoqueService;
import com.dac.ecommerce.livros.services.LivroService;

@Controller
@RequestMapping("/item-estoque")
public class ItemEstoqueController {
	
	@Autowired
	private ItemEstoqueService itemEstoqueService;
	
	@Autowired
	private EstoqueService estoqueService;
	
	@Autowired
	private LivroService livroService;

	@PostMapping("/salvar")
	public String salvar(DTOItemEstoque dtoItemEstoque) {

		ItemEstoque itemEstoque = dtoItemEstoque.toItemEstoque();
		Estoque estoque = estoqueService.bucarEstoque(dtoItemEstoque.getIdEstoqueItem());
		Livro livro = livroService.buscarLivro(dtoItemEstoque.getIdLivroItem());
		livro.setAdicionadoEmEstoque(true);
		livroService.alterarLivro(livro, livro.getId());
		
		itemEstoque.setEstoque(estoque);
		itemEstoque.setProduto(livro);
		itemEstoqueService.salvarItem(itemEstoque);
		return "redirect:/estoque/itens-estoque"; 
	}
	
	@PostMapping("/adicionar-itens")
	public String adicionarItens(DTOItemEstoque dtoItemEstoque) {
		itemEstoqueService.adicionarItem(dtoItemEstoque.getQtd(), dtoItemEstoque.getIdItem());
		return "redirect:/estoque/itens-estoque"; 
	}
	
	@PostMapping("/remover-itens")
	public String removerItens(DTOItemEstoque dtoItemEstoque) {
		itemEstoqueService.removerItem(dtoItemEstoque.getQtd(), dtoItemEstoque.getIdItem());
		return "redirect:/estoque/itens-estoque"; 
	}

	@GetMapping("/deletar-item/{id}")
	public String deletar(@PathVariable("id") Long id) {
		itemEstoqueService.deletar(id);
		return "redirect:/estoque/itens-estoque"; 
	}
	
	
}
