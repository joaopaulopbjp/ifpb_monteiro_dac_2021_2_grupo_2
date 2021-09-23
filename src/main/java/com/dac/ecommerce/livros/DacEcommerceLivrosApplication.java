package com.dac.ecommerce.livros;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dac.ecommerce.livros.model.CategoriasLivros;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.service.LivroService;

@SpringBootApplication
public class DacEcommerceLivrosApplication implements CommandLineRunner {
	
	private LivroService servicoLivro;
	
	
	public DacEcommerceLivrosApplication(LivroService servicoLivro) {
		this.servicoLivro = servicoLivro;
	}

	public static void main(String[] args) {
		SpringApplication.run(DacEcommerceLivrosApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
	
	

	
	}

	// cadastrar livro
	public void cadastrarLivro() {
		Livro livro = new Livro();
		livro.setTitulo("O Conde de Monte Cristo");
		livro.setDescricao("Um livro muito bom!");
		livro.setPreco(new BigDecimal(25.30));
		livro.setEdicao(01);
		livro.setAno(1844);
		livro.setCategoria(CategoriasLivros.AVENTURA);
		servicoLivro.salvar(livro);			
	}
	
}
