package com.dac.ecommerce.livros.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dac.ecommerce.livros.dto.DTOEstoque;
import com.dac.ecommerce.livros.model.estoque.Estoque;
import com.dac.ecommerce.livros.services.EstoqueService;
import com.dac.ecommerce.livros.services.HistoricoEstoqueService;
import com.dac.ecommerce.livros.services.ItemEstoqueService;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {
	
	@Autowired
	private EstoqueService estoqueService;

	@Autowired
	private ItemEstoqueService itemEstoqueService;
	
	@Autowired
	private HistoricoEstoqueService historicoEstoqueService;
	
	@RequestMapping("/menu-estoque")
	public String menu(Model modelEstoque) {
		List<Estoque> estoques = estoqueService.listarEstoques();
		modelEstoque.addAttribute("estoques",estoques);
		return "/estoque/menu-estoque";
	}
	
	@RequestMapping("/itens-estoque")
	public String itensEstoque(Model model) {
		model.addAttribute("itens",itemEstoqueService.bucarTodosOsItensDoEstoque());
		return "/estoque/itens-estoques";
	}
	
	@RequestMapping("/cadastrar-estoque")
	public String form(Model model) {
		model.addAttribute("dtoEstoque", new DTOEstoque());
		return "/estoque/cadastrar-estoque";
	}
	
	@RequestMapping("/historico-estoque")
	public String historico(Model model) {
		model.addAttribute("historicos",historicoEstoqueService.buscarTodos());
		return "/estoque/historico-estoque";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid @ModelAttribute("dtoEstoque")DTOEstoque dtoEstoque,BindingResult bindingResult, RedirectAttributes atts) {
		if(!bindingResult.hasErrors()) {
			Estoque estoque = dtoEstoque.toEstoque();
			estoqueService.salvarEstoque(estoque);
			return "redirect:/livro/menu-livro";
		}
		atts.addAttribute("hasErrors", true);
		return "/estoque/cadastrar-estoque";
	}
}
