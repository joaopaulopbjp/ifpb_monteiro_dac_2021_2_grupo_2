package com.dac.ecommerce.livros.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	public Categoria recuperarCategoria(String nome) throws Exception {
		
		if(nome.equals("")) {
			throw new Exception("[ERROR] CATEGORIA NÃO PODE SER CADASTRADA!");
		}
		
		Categoria categoria = categoriaRepository.findByNome(nome);
		
		if(categoria == null) {
			categoria = new Categoria();
			categoria.setNome(nome);
			categoriaRepository.save(categoria);
		}
		
		return categoria;
	}
	
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
	
	public void salvar(Categoria categoria) {
		categoriaRepository.save(categoria);
	}
	
	public Categoria buscarCategoria(Long id) {
		Categoria categoria = categoriaRepository.findById(id).get();
		return categoria;
	}
}
