package com.dac.ecommerce.livros.controller;

import java.util.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dac.ecommerce.livros.dto.DTOLivro;
import com.dac.ecommerce.livros.model.estoque.Estoque;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.services.AutorService;
import com.dac.ecommerce.livros.services.CategoriaService;
import com.dac.ecommerce.livros.services.EditoraService;
import com.dac.ecommerce.livros.services.EstoqueService;
import com.dac.ecommerce.livros.services.LivroService;

@Controller
@RequestMapping("/livro")
public class LivroController {

	@Autowired
	private AutorService autorService;

	@Autowired
	private LivroService livroService;

	@Autowired
	private EstoqueService estoqueService;

	@Autowired
	private EditoraService editoraService;

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping("/menu-livro")
	public String menu(Model model) throws Exception {

		model.addAttribute("editoras", editoraService.todasEditoras());
		model.addAttribute("categorias", categoriaService.listar());
		model.addAttribute("estoques", estoqueService.listarEstoques());
		model.addAttribute("livros", livroService.recuperarTodosOsLivros());
		model.addAttribute("autores", autorService.todosAutores());
		model.addAttribute("dtoLivro", new DTOLivro());

		return "/livro/menu-livro";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid @ModelAttribute("dtoLivro") DTOLivro dtoLivro, 
			BindingResult resultado,  RedirectAttributes atts, @RequestParam("fileLivro") MultipartFile file) {

		if(!resultado.hasErrors()) {
			Livro livro = dtoLivro.toLivro();
			Editora editora = editoraService.buscarEditora(dtoLivro.getEditora());
			Categoria categoria = categoriaService.buscarCategoria(dtoLivro.getCategoria());

			editora.getLivros().add(livro);
			categoria.getLivros().add(livro);
			livro.setCategoria(categoria);
			livro.setEditora(editora);
			try {
				if (!file.isEmpty()) {
					String inicio = "data:image/jpeg;base64,";
					String codigo = Base64.getEncoder().encodeToString(file.getBytes());
					livro.setImagemCapa(inicio + codigo);
				}
				livroService.salvar(livro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/livro/menu-livro";
		}else {
			atts.addAttribute("hasErrors", true);
			return "/livro/menu-livro";
		}
		
	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) {
		Livro livro = livroService.buscarLivro(id);
		if(livro.getEditora() != null) {
			Editora editora = livro.getEditora(); 
			for (int i = 0; i < editora.getLivros().size(); i++) {
				if(editora.getLivros().get(i).getId() == id) {
					editora.getLivros().remove(i);
					editoraService.alterarEditora(editora, editora.getId());
				}
			}
		}
		if(livro.getCategoria() != null) {
			Categoria categoria = livro.getCategoria(); 
			for (int i = 0; i < categoria.getLivros().size(); i++) {
				if(categoria.getLivros().get(i).getId() == id) {
					categoria.getLivros().remove(i);
					categoriaService.alterarCategoria(categoria, categoria.getId());
				}
			}
		}
		
		livroService.excluir(id);
		return "redirect:/livro/menu-livro";
	}
}
