package com.dac.ecommerce.livros.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dac.ecommerce.livros.dto.DTOItemPedidoUpdate;
import com.dac.ecommerce.livros.dto.DTOPedido;
import com.dac.ecommerce.livros.exceptions.PedidoException;
import com.dac.ecommerce.livros.exceptions.UsuarioException;
import com.dac.ecommerce.livros.model.pedido.Pedido;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.services.PedidoService;
import com.dac.ecommerce.livros.services.UsuarioService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired private PedidoService pedidoService;
	@Autowired private UsuarioService usuarioService;
	
	@GetMapping("/carrinho-compra")
	public String carrinhoCompras(Model model) throws UsuarioException, PedidoException {
		
		Usuario usuario = usuarioService.findById(1L);
		Pedido pedido = pedidoService.buscarPedidoNaoFinalizado(usuario.getId());
		
		model.addAttribute("dtoItemPedidoUpdate", new DTOItemPedidoUpdate());
		
		if(pedido != null) {
			model.addAttribute("itens", pedidoService.listarItemsPedido(pedido.getId()));
		} else {
			model.addAttribute("itens", new ArrayList<>());
		}
		
		return "/pedido/carrinho-compra";
	}
	
	@GetMapping("/fechar-pedido")
	public String fecharPedido(Model model) {
		
		
		
		return "/pedido/finalizar-pedido";
	}
	
	@PostMapping("/cancelar-pedido")
	public String cancelarPedido(@AuthenticationPrincipal Usuario usuario) {
		return "";
	}
	
	@GetMapping("/pedidos-finalizados")
	public String pedidosFinalizados(@AuthenticationPrincipal Usuario usuario, Model model) throws PedidoException {
		//model.addAttribute("pedidos", pedidoService.pedidosFinalizados(usuario.getId()));
		return "/pedido/pedidos-user";
	}
	
	@PostMapping("/adicionar-item")
	public String adicionarItemAoCarrinho(@ModelAttribute("dtoPedido") DTOPedido dtoPedido) throws Exception {
		
		Usuario usuario = usuarioService.findById(1L);
		
		Pedido pedido = pedidoService.gerarPedido(usuario);
		pedidoService.adicionarItemAoPedido(pedido.getId(), dtoPedido.getIdLivro(), dtoPedido.getQuantidade());
		
		
		Thread.sleep(5000);
		return "redirect:" + dtoPedido.getUrlOrigem();
		
	}

	@PostMapping("/atualizar-item")
	public String atualizarItemPedido(@ModelAttribute("dtoItemPedidoUpdate") DTOItemPedidoUpdate dtoItemPedidoUpdate) {
		
		pedidoService.atualizarItemPedido(dtoItemPedidoUpdate.getIdItemPedido(), dtoItemPedidoUpdate.getQuantidade());
		return "redirect:/pedido/carrinho-compra";
	}
	
	@GetMapping("deletar-item/{idItem}")
	public String deletarItemPedido (@PathVariable("idItem") Long idItem) {
		pedidoService.deletarItemPedido(idItem);
		return "redirect:/pedido/carrinho-compra";
	}
}
