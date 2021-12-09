package com.dac.ecommerce.livros.controller;

import java.math.BigDecimal;
import java.util.Base64;
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
import com.dac.ecommerce.livros.dto.DTOEditarLivro;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.services.AutorService;
import com.dac.ecommerce.livros.services.CategoriaService;
import com.dac.ecommerce.livros.services.EditoraService;
import com.dac.ecommerce.livros.services.EstoqueService;
import com.dac.ecommerce.livros.services.ItemEstoqueService;
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

	@Autowired
	private ItemEstoqueService itemEstoqueService;

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
	public String salvar(@Valid @ModelAttribute("dtoLivro") DTOLivro dtoLivro, BindingResult bindingResult,
			RedirectAttributes atts, @RequestParam("fileLivro") MultipartFile file, Model model) {

		if (!bindingResult.hasErrors()) {

			Livro livro = dtoLivro.toLivro();
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
		System.out.println("erro");
		model.addAttribute("editoras", editoraService.todasEditoras());
		model.addAttribute("categorias", categoriaService.listar());
		model.addAttribute("estoques", estoqueService.listarEstoques());
		model.addAttribute("livros", livroService.recuperarTodosOsLivros());
		model.addAttribute("autores", autorService.todosAutores());
		atts.addAttribute("hasErrors", true);
		return "/livro/menu-livro";
	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id) {
		livroService.excluir(id);
		return "redirect:/livro/menu-livro";
	}

	@GetMapping("/form-editar/{id}")
	public String formEditar(@PathVariable("id") Long id, Model model) {
		Livro livro = livroService.buscarLivro(id);
		DTOEditarLivro dtoEditar = new DTOEditarLivro();
		dtoEditar.setId(id);
		dtoEditar.setTitulo(livro.getTitulo());
		dtoEditar.setDescricao(livro.getDescricao());
		dtoEditar.setPreco(livro.getPreco().toString());
		model.addAttribute("dtoEditar", dtoEditar);
		return "livro/editar-livro";
	}

	@PostMapping("/editar")
	public String alterar(@Valid @ModelAttribute("dtoEditar") DTOEditarLivro dtoEditar, BindingResult bindingResult,
			RedirectAttributes atts) {

		if (!bindingResult.hasErrors()) {
			System.out.println("sem erro");
			Livro livro = livroService.buscarLivro(dtoEditar.getId());
			livro.setPreco(new BigDecimal(dtoEditar.getPreco().replace(",", ".")));
			livro.setTitulo(dtoEditar.getTitulo());
			livro.setDescricao(dtoEditar.getDescricao());
			if (livro.isAdicionadoEmEstoque()) {
				ItemEstoque itemEstoque = itemEstoqueService.buscarItemPeloLivro(livro);
				itemEstoque.setPreco(livro.getPreco());
				itemEstoqueService.alterar(itemEstoque, itemEstoque.getId());
			}
			
			livroService.alterarLivro(livro, livro.getId());
			return "redirect:/livro/menu-livro";
		}
		atts.addAttribute("hasErrors", true);
		return "livro/editar-livro";

	}
}
