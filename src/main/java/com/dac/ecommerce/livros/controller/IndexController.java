package com.dac.ecommerce.livros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.services.EstoqueService;

@Controller
public class IndexController {
	
	@Autowired
	private EstoqueService estoqueService;
	
	@GetMapping("/")
	public String index(Model model) {
		List<ItemEstoque> livrosDisponiveis = estoqueService.itensDisponiveis();
		model.addAttribute("livrosDisponiveis", livrosDisponiveis);
		return "/home/index";
	}

}
