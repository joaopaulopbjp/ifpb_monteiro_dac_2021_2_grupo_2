package com.dac.ecommerce.livros.services;


import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.PedidoException;
import com.dac.ecommerce.livros.model.pedido.Pedido;
import com.dac.ecommerce.livros.model.pedido.PedidoStatus;
import com.dac.ecommerce.livros.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public void cancelarPedido(Long id, String motivo) throws PedidoException {
		
		Pedido pedido = pedidoRepository.findById(id).get();
		
		if(pedido.getStatus() != PedidoStatus.CANCELADO) {
			if(LocalDate.now().isBefore(LocalDate.fromDateFields(pedido.getDataFechamento()))) {
				pedido.setStatus(PedidoStatus.CANCELADO);
				pedido.setMotivoCancelamento(motivo);
				salvarPedido(pedido);
			} else {
				throw new PedidoException("[ERROR CANCELAR PEDIDO] - PEDIDO FORA DO PRAZO DE CANCELAMENTO!");
			}
		} else {
			throw new PedidoException("[ERROR CANCELAR PEDIDO] - PEDIDO JÁ FOI CANCELADO!");
		}
		
	}
	
	public void salvarPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
	}
	
}
