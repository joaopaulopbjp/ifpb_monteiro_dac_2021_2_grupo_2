package com.dac.ecommerce.livros.services;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	public Categoria recuperarCategoria(String nome) {
		Categoria categoria = categoriaRepository.findByNome(nome);
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
	
	public void alterarCategoria(Categoria novaCategoria, Long id) {
		Categoria categoria = categoriaRepository.findById(id).get();
		BeanUtils.copyProperties(novaCategoria, categoria);
		categoriaRepository.save(categoria);
	}
	
	public void excluir(Long id) {
		categoriaRepository.deleteById(id);
	}
	
	public Page<Categoria> pageCategoria(int currentPage) {
		Page<Categoria> page = categoriaRepository.findAll(PageRequest.of(currentPage - 1, 5, Sort.by("nome").ascending()));
		return page;
	}
	
	
}
