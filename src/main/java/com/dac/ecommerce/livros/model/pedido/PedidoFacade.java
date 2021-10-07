package com.dac.ecommerce.livros.model.pedido;

import com.dac.ecommerce.livros.exceptions.*;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.model.user.TipoUsuario;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.services.*;

public class PedidoFacade {
	
	private Pedido pedido;
	
	// Serviços
	private PedidoService pedidoService;
	private LivroService livroService;
	private FormaPagamentoService formaPagamentoService;
	private UsuarioService usuarioService;
	
	public PedidoFacade(PedidoService pedidoService, LivroService livroService, FormaPagamentoService formaPagamentoService, UsuarioService usuarioService) {
		this.pedidoService = pedidoService;
		this.livroService = livroService;
		this.formaPagamentoService = formaPagamentoService;
		this.usuarioService = usuarioService;
	}
	
	/*
	 * Métodos uteis para a criação de um pedido.
	 * Evita criar focos de acompleto direto com o menu.
	 */
	public void criarPedido() {
		this.pedido = new Pedido();
	}
	
	public void adicionarLivro(Long idLivro, int quantidade) throws LivroException, PedidoException {
		pedidoService.adicionarItemAoPedido(pedido.getId(), idLivro, quantidade);
	}
	
	public void definirFormaPagamento(Long id) {
		FormaPagamento formaPagamento = formaPagamentoService.pesquisar(id);
		pedido.setFormaPagamento(formaPagamento);
	}
	
	public void definirCliente(Long id) throws UsuarioException {
		Usuario cliente = usuarioService.findById(id);
		
		if(cliente.getTipoUsuario() == TipoUsuario.CLIENTE) {
			pedido.setCliente(cliente);
			pedido.setEnderecoEntrega(cliente.getEndereco());
		} else {
			throw new UsuarioException("[ERRO USUÁRIO] - USUÁRIO NÃO É CLIENTE!");
		}
	}
	
	public void registrarPedido() {
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
