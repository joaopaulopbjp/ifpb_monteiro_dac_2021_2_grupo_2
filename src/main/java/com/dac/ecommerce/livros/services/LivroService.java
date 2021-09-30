package com.dac.ecommerce.livros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.LivroException;
import com.dac.ecommerce.livros.exceptions.PaginaInvalidaException;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.repository.LivroRepository;

@Service
public class LivroService {
	
	@Autowired
	private LivroRepository repositorioLivro;
	
	public void salvar(Livro livro) throws LivroException {
		
		if(livro != null){
			if(livro.getAutores() == null) {
				throw new LivroException("[ERROR] - LIVRO NÃO POSSUI AUTOR ATRELADO!");
			} else if(livro.getCategoria() == null){
				throw new LivroException("[ERROR] - LIVRO NÃO POSSUI CATEGORIA!");
			} else {
				repositorioLivro.save(livro);
			}
		} else {
			throw new LivroException("[ERROR] - LIVRO NÃO PODE SER CADASTRADO!");
		}
		
	}
	
	public void excluir(Livro livro) {
		repositorioLivro.delete(livro);
	}
	
	// Altera um livro
	public void alterar(Livro livro) {
		try {
			salvar(livro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String getAllLivrosPorPagina(Integer numeroPagina) throws PaginaInvalidaException {
		Pageable pageable = PageRequest.of((numeroPagina - 1), 5, Sort.by("titulo").ascending());
		Page<Livro> pagina = repositorioLivro.findAll(pageable);
		
		if(pagina.isEmpty()) {
			throw new PaginaInvalidaException();
		}
		
		String livros = "";
		for(Livro livro : pagina) {
			livros += livro.toString();
		}
				
		return livros;
	}


}
