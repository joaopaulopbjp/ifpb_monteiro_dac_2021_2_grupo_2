package com.dac.ecommerce.livros.services;


import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.PedidoException;
import com.dac.ecommerce.livros.model.pedido.Pedido;
import com.dac.ecommerce.livros.model.pedido.PedidosStatus;
import com.dac.ecommerce.livros.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	public void realizarPedido() {
		
	}

	public void cancelarPedido(Long id) throws PedidoException {
		
		Pedido pedido = repository.findById(id).get();
		
		LocalDate dataCriacao = LocalDate.fromDateFields(pedido.getDataCriacao());
		LocalDate dataLimiteCancelamento = dataCriacao.plusWeeks(2);
		
		if(dataCriacao.isBefore(dataLimiteCancelamento)) {
			pedido.setStatus(PedidosStatus.CANCELADO);
			salvarPedido(pedido);
		} else {
			throw new PedidoException("[ERROR CANCELAR PEDIDO] - PEDIDO FORA DO PRAZO DE CANCELAMENTO!");
		}
		
	}
	
	public void salvarPedido(Pedido pedido) {
		repository.save(pedido);
	}

}
