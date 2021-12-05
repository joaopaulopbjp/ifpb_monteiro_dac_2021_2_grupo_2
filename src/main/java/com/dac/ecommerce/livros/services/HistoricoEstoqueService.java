package com.dac.ecommerce.livros.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.estoque.HistoricoEstoque;
import com.dac.ecommerce.livros.repository.HistoricoEstoqueRepository;

@Service
public class HistoricoEstoqueService {
	
	@Autowired
	private  HistoricoEstoqueRepository repository;
	
	public void salvar(HistoricoEstoque historicoEstoque) {
		repository.save(historicoEstoque);
	}
	
	public void alterar(HistoricoEstoque historicoEstoque, Long id) {
		HistoricoEstoque estoqueH = repository.findById(id).get();
		BeanUtils.copyProperties(historicoEstoque, estoqueH);
		repository.save(estoqueH);
	}
	
	public HistoricoEstoque buscar(Long id) {
		HistoricoEstoque estoqueH = repository.findById(id).get();
		return estoqueH;
	}
	
	public void deletar(Long id) {
		HistoricoEstoque estoqueH = repository.findById(id).get();
		repository.delete(estoqueH);
	}
	
	public List<HistoricoEstoque> buscarTodos() {
		List<HistoricoEstoque> historico = repository.findAll();
		return historico;
	}
}
