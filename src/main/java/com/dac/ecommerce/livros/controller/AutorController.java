package com.dac.ecommerce.livros.controller;

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
import com.dac.ecommerce.livros.dto.DTOAutor;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.services.AutorService;

@Controller
@RequestMapping("/autor")
public class AutorController {

	@Autowired
	private AutorService autorService;
	
	@RequestMapping("/menu-autor")
	public String menu(Model model) {
		model.addAttribute("autores",autorService.todosAutores());
		model.addAttribute("dtoAutor",new DTOAutor());
		return "/autor/menu-autor";
	}
	
	@GetMapping("/cadastrar-autor")
	public String form(Model model) {
		model.addAttribute("dtoAutor",new DTOAutor());
		return "/autor/cadastrar-autor";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid @ModelAttribute("dtoAutor")DTOAutor dtoAutor, BindingResult bindingResult, RedirectAttributes atts) {
		
		if(!bindingResult.hasErrors()) {
			Autor autor = dtoAutor.toAutor();
			autorService.salvar(autor);
			return "redirect:/autor/menu-autor";
		}
		atts.addAttribute("hasErrors", true);
		return "/autor/cadastrar-autor";
	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) {
		autorService.remove(id);
		return "redirect:/autor/menu-autor";
	}

	@PostMapping("/alterar-autor")
	public String alterar(@Valid @ModelAttribute("dtoAutor") DTOAutor dtoAutor, BindingResult bindingResult, RedirectAttributes atts) {
		
		if(!bindingResult.hasErrors()) {
			Autor autor = dtoAutor.toAutor();
			System.out.println(dtoAutor);
			autorService.editarAutor(autor, autor.getId());
			return "redirect:/autor/menu-autor";
		}
		atts.addAttribute("hasErrors", true);
		return "redirect:/autor/menu-autor";
	}

}
