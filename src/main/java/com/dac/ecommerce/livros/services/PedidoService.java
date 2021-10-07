package com.dac.ecommerce.livros.services;


import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.*;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.model.pedido.*;
import com.dac.ecommerce.livros.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EstoqueService estoqueService;
	
	@Autowired
	private LivroService livroService;
	
	public void finalizarPedido(Long id) throws PedidoException {
		Pedido pedido = pedidoRepository.findById(id).get();
		
		if(pedido.getStatus() == PedidoStatus.NAO_FINALIZADO && pedido.getItens().size() > 0) {
			// Verificar estoque
			for(ItemPedido item : pedido.getItens()) {
				if(!consultarEstoque(item.getLivro().getId(), item.getQuantidade())) {
					throw new PedidoException("[ERRO FINALIZAR PEDIDO] - O ITEM ABAIXO ESTÁ SEM ESTOQUE! \n" + item.toString());
				}
			}
			
			pedido.setDataFechamento(new Date());
			pedido.setStatus(PedidoStatus.FINALIZADO);
			
			for(ItemPedido itemPedido : pedido.getItens()) {
				estoqueService.reduzirEstoque(itemPedido.getLivro(), itemPedido.getQuantidade());
			}
			
			salvarPedido(pedido);
		} else {
			throw new PedidoException("[ERRO PEDIDO] - NÃO FOI POSSÍVEL FINALIZAR O PEDIDO!");
		}

		
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
			if(consultarEstoque(idLivro, quantidade)) {
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
	
	public String detalharPedido(Long id) {
		Pedido pedido = pedidoRepository.findById(id).get();
		return pedido.toString();
	}
	
	public String listarItemsPedido(Long id) throws PedidoException {
		List<ItemPedido> itens = pedidoRepository.findCarrinhoDeCompras(id);
		
		if(itens.size() == 0) {
			throw new PedidoException("[ERROR PEDIDO] - NÃO EXISTE ITENS ADICIONADO AO PEDIDO OU PEDIDO NÃO ENCONTRADO!");
		}
		
		String carrinhoDeCompra = "";
		for(ItemPedido item : itens) {
			carrinhoDeCompra += item.toString();
		}
		
		return carrinhoDeCompra;
	}
	
	public void salvarPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
	}

	private boolean consultarEstoque(Long idLivro, int quantidade) {
		int quantidadeEstoque = estoqueService.consultarQuantidadeEmEstoque(idLivro);
		if(quantidadeEstoque >= quantidade) {
			return true;
		}
		return false;
	}
}
