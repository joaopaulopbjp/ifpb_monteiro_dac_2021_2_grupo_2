package com.dac.ecommerce.livros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@PostMapping("/salvar")
	public String salvar(DTOAutor dtoAutor) {

		Autor autor = dtoAutor.toAutor();
		autorService.salvar(autor);

		return "redirect:/livro/menu-livro";
	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) {
		autorService.remove(id);
		return "redirect:/livro/menu-livro";
	}

	@PostMapping("/alterar-autor")
	public String alterar(DTOAutor dtoAutor) {
		
		Autor autor = dtoAutor.toAutor();
		System.out.println(dtoAutor);
		autorService.editarAutor(autor, autor.getId());

		return "redirect:/livro/menu-livro";
	}

}
