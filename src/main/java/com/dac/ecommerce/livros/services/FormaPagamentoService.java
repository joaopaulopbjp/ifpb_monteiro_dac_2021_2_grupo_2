package com.dac.ecommerce.livros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.model.pedido.FormaPagamento;
import com.dac.ecommerce.livros.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	@Autowired
	private FormaPagamentoRepository repository;
	
	public void salvar(FormaPagamento formaPagamento) {
		repository.save(formaPagamento);
	}
	
	public FormaPagamento pesquisar(Long id) {
		return repository.findById(id).get();
	}
	
}
