package com.dac.ecommerce.livros.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.Autor;
import com.dac.ecommerce.livros.repository.AutorRepository;

@Service
public class AutorService {

	@Autowired
	private AutorRepository repository;
	
	public List<Autor> todosAutores(){
		
		return repository.findAll();
	}
	
	public void salvar(Autor autor) {
		
		repository.save(autor);
	}

}
