package com.dac.ecommerce.livros.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dac.ecommerce.livros.dto.DTOCategoria;
import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.services.CategoriaService;
import com.dac.ecommerce.livros.services.LivroService;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private LivroService livroService;
	
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
	
	@PostMapping("/alterar-categoria")
	public String alterar(DTOCategoria dtoCategoria) {
		Categoria categoria = dtoCategoria.toCategoria();
		categoriaService.alterarCategoria(categoria, categoria.getId());
		return "redirect:/categoria/menu-categoria";
	}
	
	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) {
		Categoria categoria = categoriaService.buscarCategoria(id);
		categoria.setLivros(null);
		categoriaService.alterarCategoria(categoria, id);
		
		Livro livro = livroService.bucarPelaCategoria(categoria);
		if(livro != null) {
			livro.setCategoria(null);
			livroService.alterarLivro(livro, livro.getId());
		}
		categoriaService.excluir(id);
		return "redirect:/categoria/menu-categoria";
	}

}
