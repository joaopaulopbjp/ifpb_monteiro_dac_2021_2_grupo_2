package com.dac.ecommerce.livros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dac.ecommerce.livros.model.estoque.HistoricoEstoque;
import com.dac.ecommerce.livros.services.HistoricoEstoqueService;

@Controller
@RequestMapping("/historico")
public class HistoricoEstoqueController {

	
	@Autowired
	private HistoricoEstoqueService historicoEstoqueService;
	
	@PostMapping("/salvar")
	private String salvar(HistoricoEstoque historicoEstoque) {
		historicoEstoqueService.salvar(historicoEstoque);
		return "redirect:/estoque/historico-estoque";
	}
	
	@PostMapping("/alterar")
	private String alterar(HistoricoEstoque historicoEstoque) {
		historicoEstoqueService.salvar(historicoEstoque);
		return "redirect:/estoque/historico-estoque";
	}
	

}
