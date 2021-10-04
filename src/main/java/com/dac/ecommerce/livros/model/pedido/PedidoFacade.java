package com.dac.ecommerce.livros.model.pedido;

import com.dac.ecommerce.livros.exceptions.*;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.services.*;

public class PedidoFacade {
	
	private Pedido pedido;
	private PedidoService pedidoService;
	private EstoqueService estoqueService;
	private LivroService livroService;
	private FormaPagamentoService formaPagamentoService;
	
	public PedidoFacade(PedidoService pedidoService, EstoqueService estoqueService, LivroService livroService, FormaPagamentoService formaPagamentoService) {
		this.pedidoService = pedidoService;
		this.estoqueService = estoqueService;
		this.livroService = livroService;
		this.formaPagamentoService = formaPagamentoService;
	}
	
	// Métodos
	public void criarPedido() {
		this.pedido = new Pedido();
	}
	
	public void definirEnderecoEntrega(String cep, Integer numero, String cidade, String estado, String bairro, String rua, String complemento) throws PedidoException {
		if(pedido != null) {
			Endereco enderecoEntrega = new Endereco(cep, numero, cidade, estado, bairro, rua, complemento);
			pedido.setEnderecoEntrega(enderecoEntrega);
		} else {
			throw new PedidoException("[ERROR ENDEREÇO DE ENTREGA] - NENHUM PEDIDO FOI INICIADO!");
		}
	}

	public void adicionarLivro(Long id, int quantidade) throws LivroException, PedidoException {
		
		if(pedido != null) {
			int quantidadeEstoque = estoqueService.consultarQuantidadeEmEstoque(id);
			if(quantidadeEstoque > 0 && (quantidadeEstoque - quantidade) >= 0) {
				Livro livro = livroService.buscarLivro(id);
				ItemPedido itemPedido = new ItemPedido(livro, quantidade);
				itemPedido.setPedido_fk(pedido);
				pedido.adicionarItem(itemPedido);
			} else {
				throw new PedidoException("[ERROR ADICIONAR ITEM] - LIVRO SEM ESTOQUE!");
			}
		} else {
			throw new PedidoException("[ERROR ADICIONAR ITEM] - NENHUM PEDIDO FOI INICIADO!");
		}
	
	}
	
	public void definirFormaPagamento(Long id) {
		FormaPagamento formaPagamento = formaPagamentoService.pesquisar(id);
		pedido.setFormaPagamento(formaPagamento);
	}
	
	public void finalizarPedido() {
		
		// Reduzir o estoque
		for(ItemPedido itemPedido : pedido.getItens()) {
			estoqueService.reduzirEstoque(itemPedido.getLivro(), itemPedido.getQuantidade());
		}
		
		pedidoService.salvarPedido(pedido);
	}

	public void imprimirLivros() {
		try {
			for(Livro livro : livroService.recuperarTodosOsLivros()) {
				System.out.println(livro.toString());
			}
		} catch (Exception erro) {
			System.out.println(erro.getMessage());
		}
	}
	
	public void imprimirFormasPagamento() {
		for(FormaPagamento formaPagamento : formaPagamentoService.listar()) {
			if(formaPagamento.getIsActive()) {
				System.out.println(formaPagamento.toString());
			}
		}
	}
}
