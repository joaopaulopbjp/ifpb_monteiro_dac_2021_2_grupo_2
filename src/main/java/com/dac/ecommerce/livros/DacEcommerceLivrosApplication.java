package com.dac.ecommerce.livros;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.dac.ecommerce.livros.model.Autor;
import com.dac.ecommerce.livros.model.CategoriasLivros;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.service.AutorService;
import com.dac.ecommerce.livros.service.LivroService;

@SpringBootApplication
public class DacEcommerceLivrosApplication implements CommandLineRunner {
	
	private LivroService servicoLivro;

	private AutorService autorService;
	
	public DacEcommerceLivrosApplication(LivroService servicoLivro, AutorService autorService) {
		this.servicoLivro = servicoLivro;
		this.autorService = autorService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DacEcommerceLivrosApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		cadastrarLivro();
	
	}

	// teste cadastrar livro
	public void cadastrarLivro() {
		
		Autor autor1 = new Autor();
		autor1.setNome("fulano");
		autorService.salvar(autor1);
		
		//recuperando autores
		List<Autor> autores = autorService.todosAutores();
		
		Livro novoLivro = new Livro();
		novoLivro.setIsbn("1234");
		novoLivro.setTitulo("Livro 1");
		novoLivro.setDescricao("primeiro livro");
		novoLivro.setPreco(new BigDecimal(20.5));
		novoLivro.setEdicao(01);
		novoLivro.setAno(2021);
		novoLivro.setCategoria(CategoriasLivros.AVENTURA);
		novoLivro.setAutores(autores);
		
		try {
			servicoLivro.salvar(novoLivro);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
	}
	
}
