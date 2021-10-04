package com.dac.ecommerce.livros.services;


import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.LivroException;
import com.dac.ecommerce.livros.exceptions.PedidoException;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.model.pedido.ItemPedido;
import com.dac.ecommerce.livros.model.pedido.Pedido;
import com.dac.ecommerce.livros.model.pedido.PedidoStatus;
import com.dac.ecommerce.livros.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EstoqueService estoqueService;
	
	@Autowired
	private LivroService livroService;
	
	public void finalizazrPedido(Long id) {
		Pedido pedido = pedidoRepository.findById(id).get();
		pedido.setDataFechamento(new Date());
		pedido.setStatus(PedidoStatus.FINALIZADO);
		
		for(ItemPedido itemPedido : pedido.getItens()) {
			estoqueService.reduzirEstoque(itemPedido.getLivro(), itemPedido.getQuantidade());
		}
		
		salvarPedido(pedido);
	}
	
	public void cancelarPedido(Long id, String motivo) throws PedidoException {
		
		Pedido pedido = pedidoRepository.findById(id).get();
		
		if(pedido.getStatus() != PedidoStatus.CANCELADO) {
			LocalDate dataAtual = LocalDate.now();
			LocalDate dataLimiteCancelamento = LocalDate.fromDateFields(pedido.getDataFechamento()).plusWeeks(1);
			if(dataAtual.isBefore(dataLimiteCancelamento) || dataAtual.isEqual(dataLimiteCancelamento)) {
				pedido.setStatus(PedidoStatus.CANCELADO);
				pedido.setMotivoCancelamento(motivo);
				
				// Desenvolver items para o estoque
				for(ItemPedido itemPedido : pedido.getItens()) {
					estoqueService.reporEstoquePedidoCancelado(itemPedido.getLivro(), itemPedido.getQuantidade());
				}
				
				salvarPedido(pedido);
			} else {
				throw new PedidoException("[ERROR CANCELAR PEDIDO] - PEDIDO FORA DO PRAZO DE CANCELAMENTO!");
			}
		} else {
			throw new PedidoException("[ERROR CANCELAR PEDIDO] - PEDIDO JÁ FOI CANCELADO!");
		}
		
	}
	
	public void adicionarItemAoPedido(Long idPedido, Long idLivro, int quantidade) throws LivroException, PedidoException {
		Pedido pedido = pedidoRepository.findById(idPedido).get();
		if(pedido != null) {
			int quantidadeEstoque = estoqueService.consultarQuantidadeEmEstoque(idLivro);
			if(quantidadeEstoque > 0 && (quantidadeEstoque - quantidade) >= 0) {
				Livro livro = livroService.buscarLivro(idLivro);
				ItemPedido itemPedido = new ItemPedido(livro, quantidade);
				itemPedido.setPedido_fk(pedido);
				pedido.adicionarItem(itemPedido);
				salvarPedido(pedido);
			} else {
				throw new PedidoException("[ERROR ADICIONAR ITEM] - LIVRO SEM ESTOQUE!");
			}
		} else {
			throw new PedidoException("[ERROR ADICIONAR ITEM] - NENHUM PEDIDO FOI INICIADO!");
		}
	}
	
	public void salvarPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
	}

}
