package com.dac.ecommerce.livros.services;


import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.*;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.model.pedido.*;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.repository.FormaPagamentoRepository;
import com.dac.ecommerce.livros.repository.ItemPedidoRepository;
import com.dac.ecommerce.livros.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired private PedidoRepository pedidoRepository;
	@Autowired private ItemPedidoRepository itemPedidoRepository;
	@Autowired private FormaPagamentoRepository formaPagamentoRepository;
	@Autowired private EstoqueService estoqueService;
	@Autowired private LivroService livroService;
	 
	public Pedido gerarPedido(Usuario cliente) throws PedidoException {
		
		 // Gera um novo pedido caso o cliente ainda não possue um pedido criado
		 // E retorna o pedido caso o cliente já tenha criado
		
		Pedido pedido = buscarPedidoNaoFinalizado(cliente.getId());
				
		if(pedido != null) {
			return pedido;
		}
		
		pedido = new Pedido();
		pedido.setCliente(cliente);
		
		if(cliente.getEndereco() == null) {
			throw new PedidoException("[ERROR PEDIDO] - NÃO EXISTE ENDEREÇO DE ENTREGA CADASTRADO.");
		}
		
		pedido.setEnderecoEntrega(cliente.getEndereco());
		
		return pedidoRepository.save(pedido);
	}
	
	public Pedido buscarPedidoNaoFinalizado(Long idCliente) {
		return pedidoRepository.findPedidoNaoFinalizado(idCliente);
	}
	
	public void finalizarPedido(Long id, String formaPagamento) throws PedidoException, EstoqueException {
		
		Pedido pedido = pedidoRepository.findById(id).get();
		
		if(pedido.getStatus() == PedidoStatus.NAO_FINALIZADO && pedido.getItens().size() > 0) {
			
			if(formaPagamento.length() > 0) {
				// Verificar estoque
				for(ItemPedido item : pedido.getItens()) {
					if(!consultarEstoque(item.getLivro().getId(), item.getQuantidade())) {
						throw new PedidoException("[ERRO FINALIZAR PEDIDO] - O ITEM ABAIXO ESTÁ SEM ESTOQUE! \n" + item.toString());
					}
				}
				
				// Reduzir Estoque
				for(ItemPedido itemPedido : pedido.getItens()) {
					estoqueService.reduzirEstoque(itemPedido.getLivro(), itemPedido.getQuantidade());
				}
				
				pedido.setFormaPagamento(formaPagamentoRepository.findByTipo(formaPagamento));
				pedido.setDataFechamento(new Date());
				pedido.setStatus(PedidoStatus.FINALIZADO);
				
				salvarPedido(pedido);
			}
		} else {
			throw new PedidoException("[ERRO PEDIDO] - NÃO FOI POSSÍVEL FINALIZAR O PEDIDO!");
		}

	}
	
	public void cancelarPedido(Long id, String motivo) throws PedidoException {
		
		Pedido pedido = pedidoRepository.findById(id).get();
		
		if(pedido.getStatus() != PedidoStatus.CANCELADO) {
			
			// Obter data limite de cancelamento adicionado 1 semana depois da data de fechamento
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
			if(consultarEstoque(idLivro, quantidade)) {								// Verificar se existe estoque do livro
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
	
	public void atualizarItemPedido(Long idItemPedido, int quantidade) {
		ItemPedido itemPedido = itemPedidoRepository.findById(idItemPedido).get();
		itemPedido.setQuantidade(quantidade);
		itemPedidoRepository.save(itemPedido);
	}
	
	public void deletarItemPedido(Long idItemPedido) {
		ItemPedido itemPedido = itemPedidoRepository.findById(idItemPedido).get();
		Pedido pedido = itemPedido.getPedido_fk();
		pedido.removerItem(itemPedido);
		itemPedidoRepository.deleteById(idItemPedido);
		pedidoRepository.save(pedido);
	}
	
	public String detalharPedido(Long id) {
		Pedido pedido = pedidoRepository.findById(id).get();
		return pedido.toString();
	}
	
	public List<ItemPedido> listarItemsPedido(Long id) throws PedidoException {	
		return pedidoRepository.findCarrinhoDeCompras(id);
	}
	
	public List<Pedido> pedidosFinalizados(Long idCliente) {
		return pedidoRepository.findPedidosConcluidos(idCliente);
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
