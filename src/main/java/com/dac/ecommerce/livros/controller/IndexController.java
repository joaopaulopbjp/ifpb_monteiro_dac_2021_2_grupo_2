package com.dac.ecommerce.livros.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dac.ecommerce.livros.dto.DTOPedido;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.services.CategoriaService;
import com.dac.ecommerce.livros.services.EstoqueService;

@Controller
public class IndexController {
	
	@Autowired private EstoqueService estoqueService;
	@Autowired private CategoriaService categoriaService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("livrosDisponiveis",  estoqueService.itensDisponiveis());
		model.addAttribute("categorias", categoriaService.listar());
		return "/home/index";
	}
	
	@GetMapping("/detalhar-livro/{id}")
	public String detalharLivro(@PathVariable("id") Long id, Model model) {
		ItemEstoque itemEstoque = estoqueService.pesquisarItemEstoque(id);
		model.addAttribute("item", itemEstoque);
		model.addAttribute("dtoPedido", new DTOPedido());
		return "/home/detalhar-item";
	}
	
	@GetMapping("/pesquisar/{titulo}")
	public String pesquisarLivro(@PathVariable("titulo") String titulo, Model model) {
		
		List<ItemEstoque> item = new ArrayList<ItemEstoque>();
		
		ItemEstoque itemEstoque = estoqueService.pesquisarItemEstoquePorLivro(titulo);
		item.add(itemEstoque);
		
		model.addAttribute("livrosDisponiveis", item);
		model.addAttribute("categorias", categoriaService.listar());
		return "/home/index";
	}
	
	@GetMapping("/categoria/{nome}")
	public String pesquisarCategoria(@PathVariable("nome") String nome, Model model) {
		List<ItemEstoque> itens = estoqueService.itensPorCategoria(nome);
		model.addAttribute("livrosDisponiveis", itens);
		model.addAttribute("categorias", categoriaService.listar());
		return "/home/index";
	}

}
