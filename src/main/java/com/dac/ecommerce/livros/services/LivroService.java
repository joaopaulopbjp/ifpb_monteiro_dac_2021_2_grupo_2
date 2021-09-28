package com.dac.ecommerce.livros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.PaginaInvalidaException;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.repository.LivroRepository;

@Service
public class LivroService {
	
	@Autowired
	private LivroRepository repositorioLivro;
	
	public void salvar(Livro livro) throws Exception{
		
		if(livro != null){
			if(livro.getAutores() == null || livro.getAutores().size() == 0) {
				throw new Exception("Livro não possui autor!");
			}else if(livro.getCategoria() == null){
				throw new Exception("Livro não possui categoria!");
			}else {
				repositorioLivro.save(livro);
			}
		}else {
			throw new Exception("Livro não pode ser cadastrado!");
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
			System.out.println(e);
		}
	}
	
	// Recupera todos os livros
	public String getAllLivrosPorPagina(Integer numeroPagina) throws PaginaInvalidaException {
		Pageable pageable = PageRequest.of((numeroPagina - 1), 5, Sort.by("titulo").ascending());
		Page<Livro> pagina = repositorioLivro.findAll(pageable);
		
		String livros = "";
		for(Livro livro : pagina) {
			livros += livro.toString();
		}
		
		if(livros.length() == 0) {
			throw new PaginaInvalidaException();
		}
		return livros;
	}
	

}
