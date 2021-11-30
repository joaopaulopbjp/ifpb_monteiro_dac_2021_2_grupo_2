package com.dac.ecommerce.livros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dac.ecommerce.livros.dto.DTOEditora;
import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.services.EditoraService;

@Controller
@RequestMapping("/editora")
public class EditoraController {
	
	@Autowired
	private EditoraService editoraService;
	
	@RequestMapping("/menu-editora")
	public String menu(Model modelEditora) {
		List<Editora> editoras = editoraService.todasEditoras();
		modelEditora.addAttribute("editoras", editoras);
		return "/editora/menu-editora";
	}
	
	@RequestMapping("/cadastrar-editora")
	public String form() {
		return "/editora/cadastrar-editora";
	}
	
	@PostMapping("/salvar-editora")
	public String salvar(DTOEditora dtoEditora) {
		Editora editora = dtoEditora.toEditora();
		editoraService.salvar(editora);
		return "redirect:/editora/menu-editora";
	}

}
