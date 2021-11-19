package com.dac.ecommerce.livros.controller;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.dac.ecommerce.livros.dto.DTOLivro;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.services.AutorService;
import com.dac.ecommerce.livros.services.LivroService;

@Controller
@RequestMapping("/livro")
public class LivroController {
	
	@Autowired
	private AutorService autorService;
	
	@Autowired
	private LivroService livroService;

	@RequestMapping("/index")
	public String indexLivro(Model modelLivros) throws Exception {
		List<Livro> livros = livroService.recuperarTodosOsLivros();
		modelLivros.addAttribute("livros", livros);
		return "/livro/index";
	}
	
	@RequestMapping("/cadastrar")
	public String cadastrar(Model modelAutor, Model editora, Model categoria) throws Exception {
		List<Autor> autores = autorService.todosAutores();
		modelAutor.addAttribute("autores",autores);
		editora.addAttribute("editora", editora);
		categoria.addAttribute("categoria", categoria);
		return "/livro/cadastrar";
	}
	
	@PostMapping("/salvar")
	public String salvar(DTOLivro dtoLivro, @RequestParam("fileLivro") MultipartFile file){
		Livro livro = dtoLivro.toLivro();
		try {
			String inicio = "data:image/jpeg;base64,";
			String codigo = Base64.getEncoder().encodeToString(file.getBytes());
			livro.setImagemCapa(inicio+codigo);
			livroService.salvar(livro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/livro/index";
	}
	
	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable("id") Long id){
		livroService.excluir(id);
		return "redirect:/livro/index";
	}
	
}
