package com.dac.ecommerce.livros.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.model.pedido.FormaPagamento;
import com.dac.ecommerce.livros.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public void salvar(FormaPagamento formaPagamento) {
		
		if(formaPagamentoRepository.findByTipo(formaPagamento.getTipo()) == null) {
			formaPagamentoRepository.save(formaPagamento);
		}
		
	}
	
	public void remover(Long id) {
		formaPagamentoRepository.deleteById(id);
	}
	
	public void desativar(Long id) {
		FormaPagamento fp = formaPagamentoRepository.findById(id).get();
		fp.setIsActive(false);
		formaPagamentoRepository.save(fp);
	}
	
	public FormaPagamento pesquisar(Long id) {
		return formaPagamentoRepository.findById(id).get();
	}
	
	public List<FormaPagamento> listar() {
		return formaPagamentoRepository.findAll();
	}
	
	public List<FormaPagamento> buscarFormasAtivas() {
		return formaPagamentoRepository.findByActive();
	}

	public void atualizar(Long id) {
		FormaPagamento formaPagamento = pesquisar(id);
		formaPagamento.setIsActive(formaPagamento.getIsActive() == true ? false : true);
		formaPagamentoRepository.save(formaPagamento);
	}
	
}
