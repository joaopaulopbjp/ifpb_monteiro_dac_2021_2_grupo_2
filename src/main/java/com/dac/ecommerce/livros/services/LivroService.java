package com.dac.ecommerce.livros.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
	//altera um livro
	public void alterar(Livro livro) {
		try {
			salvar(livro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//recupera todos os livros
	public List<Livro> todosOsLivros() {
	
		return repositorioLivro.findAll();
	}
	

}
