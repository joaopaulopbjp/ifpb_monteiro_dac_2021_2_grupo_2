package com.dac.ecommerce.livros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dac.ecommerce.livros.dto.DTOEstoque;
import com.dac.ecommerce.livros.model.estoque.Estoque;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.services.EstoqueService;
import com.dac.ecommerce.livros.services.ItemEstoqueService;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {
	
	@Autowired
	private EstoqueService estoqueService;

	@Autowired
	private ItemEstoqueService itemEstoqueService;
	
	@RequestMapping("/menu-estoque")
	public String menu(Model modelItemEstoque, Model modelEstoque) {
		
		List<ItemEstoque> itens = itemEstoqueService.bucarTodosOsItensDoEstoque();
		modelItemEstoque.addAttribute("itens",itens);
		
		List<Estoque> estoques = estoqueService.listarEstoques();
		modelEstoque.addAttribute("estoques",estoques);
		
		return "/estoque/menu-estoque";
	}
	
	@RequestMapping("/cadastrar-estoque")
	public String form() {
		return "/estoque/cadastrar-estoque";
	}
	
	@RequestMapping("/historico-estoque")
	public String historico() {
		return "/estoque/historico-estoque";
	}
	
	
	@PostMapping("/salvar")
	public String salvar(DTOEstoque dtoEstoque) {
		
		Estoque estoque = dtoEstoque.toEstoque();
		estoqueService.salvarEstoque(estoque);
		return "redirect:/livro/menu-livro";
	}
}
