package com.dac.ecommerce.livros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dac.ecommerce.livros.dto.DTOAutor;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.services.AutorService;

@Controller
@RequestMapping("/autor")
public class AutorController {
	
	@Autowired
	private AutorService autorService;
	
	@RequestMapping("/menu-autor")
	public String menu(Model modelAutor) {
		List<Autor> autores = autorService.todosAutores();
		modelAutor.addAttribute("autores", autores);
		return "/autor/menu-autor";
	}
	
	@RequestMapping("/cadastrar-autor")
	public String cadastrar() {
		return "/autor/cadastrar-autor";
	}
	
	@PostMapping("/salvar")
	public String salvar(DTOAutor dtoAutor) {
		
		Autor autor = dtoAutor.toAutor();
		autorService.salvar(autor);
		
		return "redirect:/autor/menu-autor";
	}
	
	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) {
		autorService.remove(id);
		return "redirect:/autor/menu-autor";
	}
	
	@PostMapping("/alterar-autor")
	public String alterar(DTOAutor dtoAutor) {
		
		Autor autor = dtoAutor.toAutor();
		System.out.println(dtoAutor);
		autorService.editarAutor(autor, autor.getId());
		
		return "redirect:/autor/menu-autor"; 
	}

}
