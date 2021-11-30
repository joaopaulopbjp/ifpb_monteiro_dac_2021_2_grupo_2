package com.dac.ecommerce.livros.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String menu(Model modelEstoques, Model modelLivros, Model modelAutores, Model modelEditoras,
			Model modelCategorias) throws Exception {

		List<Editora> editoras = editoraService.todasEditoras();
		modelEditoras.addAttribute("editoras", editoras);

		List<Categoria> categorias = categoriaService.listar();
		modelCategorias.addAttribute("categorias", categorias);

		List<Estoque> estoques = estoqueService.listarEstoques();
		modelEstoques.addAttribute("estoques", estoques);

		List<Livro> livros = livroService.recuperarTodosOsLivros();
		modelLivros.addAttribute("livros", livros);

		List<Autor> autores = autorService.todosAutores();
		modelAutores.addAttribute("autores", autores);

		return "/livro/menu-livro";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("dtoLivro") DTOLivro dtoLivro, 
			@RequestParam("fileLivro") MultipartFile file) {
		
		Livro livro = dtoLivro.toLivro();
		Editora editora = editoraService.buscarEditora(dtoLivro.getEditora());
		Categoria categoria = categoriaService.buscarCategoria(dtoLivro.getCategoria());
		livro.setEditora(editora);
		livro.setCategoria(categoria);
		
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
	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) {
		livroService.excluir(id);
		return "redirect:/livro/menu-livro";
	}
}
