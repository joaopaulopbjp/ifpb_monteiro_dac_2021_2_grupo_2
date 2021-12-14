package com.dac.ecommerce.livros.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
	public String menu(Model model) {
		return listByPage(model, 1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage) {

		Page<Categoria> page = categoriaService.pageCategoria(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<Categoria> categorias = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("categorias", categorias);
		model.addAttribute("dtoCategoria", new DTOCategoria());

		return "/categoria/menu-categoria";
	}

	@RequestMapping("/cadastrar-categoria")
	public String form(Model model) {
		model.addAttribute("dtoCategoria", new DTOCategoria());
		return "/categoria/cadastrar-categoria";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid @ModelAttribute("dtoCategoria") DTOCategoria dtoCategoria, BindingResult bindingResult,
			RedirectAttributes atts) {
		if (!bindingResult.hasErrors()) {
			Categoria categoria = dtoCategoria.toCategoria();
			categoriaService.salvar(categoria);
			return "redirect:/categoria/menu-categoria";
		}
		atts.addAttribute("hasErrors", true);
		return "/categoria/cadastrar-categoria";
	}

	@GetMapping("/editar/{id}")
	String formEditar(@PathVariable("id") Long id, Model model) {

		Categoria categoria = categoriaService.buscarCategoria(id);
		DTOCategoria dtoCategoria = new DTOCategoria();
		dtoCategoria.setIdCategoria(categoria.getId());
		dtoCategoria.setNomeCategoria(categoria.getNome());
		model.addAttribute("dtoCategoria", dtoCategoria);

		return "/categoria/editar-categoria";
	}

	@PostMapping("/alterar-categoria")
	public String alterar(@Valid @ModelAttribute("dtoCategoria") DTOCategoria dtoCategoria, BindingResult bindingResult,
			RedirectAttributes atts, Model model) {

		if (!bindingResult.hasErrors()) {
			Categoria categoria = dtoCategoria.toCategoria();
			categoriaService.alterarCategoria(categoria, categoria.getId());
			return "redirect:/categoria/menu-categoria";
		}
		model.addAttribute("categorias", categoriaService.listar());
		atts.addAttribute("hasErrors", true);
		return "/categoria/editar-categoria";

	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) {

		List<Livro> livros = livroService.recuperarTodosOsLivros();
		boolean result = false;
		if (!livros.isEmpty()) {
			for (Livro livro : livros) {
				if (livro.getCategoria().getId() == id) {
					result = true;
					break;
				}
			}
		}
		if (result == false) {
			categoriaService.excluir(id);
			return "redirect:/categoria/menu-categoria";
		}
		return "/categoria/erro-excluir-categoria";
	}
}
