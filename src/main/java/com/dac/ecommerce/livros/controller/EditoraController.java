package com.dac.ecommerce.livros.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dac.ecommerce.livros.dto.DTOEditora;
import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.services.EditoraService;
import com.dac.ecommerce.livros.services.LivroService;

@Controller
@RequestMapping("/editora")
public class EditoraController {
	
	@Autowired
	private EditoraService editoraService;
	
	@Autowired
	private LivroService livroService;
	
	@RequestMapping("/menu-editora")
	public String menu(Model model) {
		model.addAttribute("editoras", editoraService.todasEditoras());
		return "/editora/menu-editora";
	}
	
	@RequestMapping("/cadastrar-editora")
	public String form(Model model) {
		model.addAttribute("dtoEditora", new DTOEditora());
		return "/editora/cadastrar-editora";
	}
	
	@PostMapping("/salvar-editora")
	public String salvar(@Valid @ModelAttribute("dtoEditora")DTOEditora dtoEditora,BindingResult bindingResult, RedirectAttributes atts) {
		if(!bindingResult.hasErrors()) {
			Editora editora = dtoEditora.toEditora();
			editoraService.salvar(editora);
			return "redirect:/editora/menu-editora";
			
		}
		atts.addAttribute("hasErrors", true);
		return "/editora/cadastrar-editora";
	}
	
	@PostMapping("/alterar-editora")
	public String alterar(@Valid @ModelAttribute("dtoEditora") DTOEditora dtoEditora,BindingResult bindingResult, RedirectAttributes atts) {
		if(!bindingResult.hasErrors()) {
			Editora editora = dtoEditora.toEditora();
			editoraService.alterarEditora(editora, editora.getId());
			return "redirect:/editora/menu-editora";
		}
		atts.addAttribute("hasErrors", true);
		return "redirect:/editora/menu-editora";
	}
	
	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) {

		List<Livro> livros = livroService.recuperarTodosOsLivros();
		boolean result = false;
		if(!livros.isEmpty()) {
			for (Livro livro : livros) {
				if(livro.getEditora().getId() == id) {
					result = true;
				}
			}
		}
		if(result == false) {
			editoraService.excluirEditora(id);
		}
		
		return "redirect:/editora/menu-editora";
	}
}
