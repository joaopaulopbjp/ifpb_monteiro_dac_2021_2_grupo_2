package com.dac.ecommerce.livros.services;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.repository.EstoqueRepository;

@Service
public class EstoqueService {
	
	@Autowired
	private EstoqueRepository repository;

	//adicionar no estoque atual
	public void adicionarNoEstoque(ArrayList<Livro> livros) {
		
	}

}
