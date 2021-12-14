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
import com.dac.ecommerce.livros.dto.DTOAutor;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.services.AutorService;
import com.dac.ecommerce.livros.services.LivroService;

@Controller
@RequestMapping("/autor")
public class AutorController {

	@Autowired
	private AutorService autorService;

	@Autowired
	private LivroService livroService;

	@RequestMapping("/menu-autor")
	public String menu(Model model) {
		return listByPage(model, 1);
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable("pageNumber") int currentPage) {

		Page<Autor> page = autorService.pageAutor(currentPage);
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();

		List<Autor> autores = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("autores", autores);
		model.addAttribute("dtoAutor", new DTOAutor());

		return "/autor/menu-autor";
	}

	@GetMapping("/cadastrar-autor")
	public String form(Model model) {
		model.addAttribute("dtoAutor", new DTOAutor());
		return "/autor/cadastrar-autor";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid @ModelAttribute("dtoAutor") DTOAutor dtoAutor, BindingResult bindingResult,
			RedirectAttributes atts) {

		if (!bindingResult.hasErrors()) {
			Autor autor = dtoAutor.toAutor();
			autorService.salvar(autor);
			return "redirect:/autor/menu-autor";
		}
		atts.addAttribute("hasErrors", true);
		return "/autor/cadastrar-autor";
	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) throws Exception {

		List<Livro> livros = livroService.recuperarTodosOsLivros();
		Autor autor = autorService.recuperarAutor(id);
		boolean result = false;
		if (!livros.isEmpty()) {
			for (int i = 0; i < livros.size(); i++) {
				if (livros.get(i).getAutores().contains(autor)) {
					result = true;
					break;
				}
			}
		}
		if (result == false) {
			autorService.remove(id);
			return "redirect:/autor/menu-autor";
		}
		return "/autor/erro-excluir-autor";
	}

	@GetMapping("/form-editar/{id}")
	public String formEditar(@PathVariable("id") Long id, Model model) throws Exception {
		Autor autor = autorService.recuperarAutor(id);
		DTOAutor dtoEditar = new DTOAutor();
		dtoEditar.setIdAutor(autor.getId());
		dtoEditar.setNomeAutor(autor.getNome());
		model.addAttribute("dtoAutor", dtoEditar);
		return "autor/editar-autor";
	}

	@PostMapping("/alterar-autor")
	public String alterar(@Valid @ModelAttribute("dtoAutor") DTOAutor dtoAutor, BindingResult bindingResult,
			RedirectAttributes atts, Model model) throws Exception {

		if (!bindingResult.hasErrors()) {
			Autor autor = dtoAutor.toAutor();
			autorService.editarAutor(autor, autor.getId());
			return "redirect:/autor/menu-autor";
		}
		atts.addAttribute("hasErrors", true);
		return "autor/editar-autor";
	}
}
