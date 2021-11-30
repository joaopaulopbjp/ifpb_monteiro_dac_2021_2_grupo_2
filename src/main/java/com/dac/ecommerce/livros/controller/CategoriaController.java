package com.dac.ecommerce.livros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dac.ecommerce.livros.dto.DTOCategoria;
import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.services.CategoriaService;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping("/menu-categoria")
	public String menu(Model modelCategorias) {
		List<Categoria> categorias = categoriaService.listar();
		modelCategorias.addAttribute("categorias", categorias);
		return "/categoria/menu-categoria";
	}
	
	@RequestMapping("/cadastrar-categoria")
	public String form() {
		return "/categoria/cadastrar-categoria";
	}
	
	@PostMapping("/salvar")
	public String salvar(DTOCategoria dtoCategoria) {
		Categoria categoria = dtoCategoria.toCategoria();
		categoriaService.salvar(categoria);
		return "redirect:/categoria/menu-categoria";
	}

}
