package com.dac.ecommerce.livros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.repository.LivroRepository;

@Service
public class LivroService {
	
	@Autowired
	private LivroRepository repositorioLivro;
	
	public void salvar(Livro livro) {
		repositorioLivro.save(livro);
	}
	
	public void excluir(Livro livro) {
		repositorioLivro.delete(livro);
	}
	
	public void alterar(Livro livro) {
		salvar(livro);
	}

}
