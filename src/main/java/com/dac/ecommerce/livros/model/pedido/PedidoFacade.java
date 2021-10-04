package com.dac.ecommerce.livros.model.pedido;

import com.dac.ecommerce.livros.exceptions.*;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.services.*;

public class PedidoFacade {
	
	private Pedido pedido;
	private PedidoService pedidoService;
	private LivroService livroService;
	private FormaPagamentoService formaPagamentoService;
	
	public PedidoFacade(PedidoService pedidoService, LivroService livroService, FormaPagamentoService formaPagamentoService) {
		this.pedidoService = pedidoService;
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

	public void adicionarLivro(Long idLivro, int quantidade) throws LivroException, PedidoException {
		pedidoService.adicionarItemAoPedido(pedido.getId(), idLivro, quantidade);
	}
	
	public void definirFormaPagamento(Long id) {
		FormaPagamento formaPagamento = formaPagamentoService.pesquisar(id);
		pedido.setFormaPagamento(formaPagamento);
	}
	
	public void registrarPedido() {
		pedidoService.salvarPedido(pedido);
	}
	
	public void finalizarPedido() {
		pedidoService.finalizazrPedido(pedido.getId());
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
