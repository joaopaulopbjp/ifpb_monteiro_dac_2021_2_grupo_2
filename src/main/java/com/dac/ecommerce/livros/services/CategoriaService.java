package com.dac.ecommerce.livros.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.Categoria;
import com.dac.ecommerce.livros.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	public void salvarCategoria(String nome) throws Exception{
		if(nome.equals("")) {
			throw new Exception("[ERROR] CATEGORIA NÃO PODE SER CADASTRADA!");
		}
		Categoria categoria = new Categoria();
		categoria.setNome(nome);
		categoriaRepository.save(categoria);
	}
	
	
}