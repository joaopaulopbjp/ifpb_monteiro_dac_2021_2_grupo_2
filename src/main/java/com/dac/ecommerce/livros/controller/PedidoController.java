package com.dac.ecommerce.livros.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.dac.ecommerce.livros.dto.DTOItemPedidoUpdate;
import com.dac.ecommerce.livros.dto.DTOPedido;
import com.dac.ecommerce.livros.dto.DTOPedidoCancelado;
import com.dac.ecommerce.livros.exceptions.PedidoException;
import com.dac.ecommerce.livros.model.pedido.FormaPagamento;
import com.dac.ecommerce.livros.model.pedido.Pedido;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.services.FormaPagamentoService;
import com.dac.ecommerce.livros.services.PedidoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired private PedidoService pedidoService;
	@Autowired private FormaPagamentoService formaPagamentoService;
	
	@GetMapping("/carrinho-compra")
	public String carrinhoCompras(@AuthenticationPrincipal Usuario usuario, Model model) {
		
		try {
			List<FormaPagamento> formasPagamento = formaPagamentoService.buscarFormasAtivas();
			Pedido pedido = pedidoService.buscarPedidoNaoFinalizado(usuario.getId());
			
			model.addAttribute("dtoItemPedidoUpdate", new DTOItemPedidoUpdate());
			model.addAttribute("formasPagamento", formasPagamento);
			
			if(pedido != null) {
				model.addAttribute("itens", pedidoService.listarItemsPedido(pedido.getId()));
			} else {
				model.addAttribute("itens", new ArrayList<>());
			}
			
			return "/pedido/carrinho-compra";
		} catch(Exception error) {
			return "redirec:/user/menu-conta";
		}
	
	}
	
	@GetMapping("/finalizar-pedido/{id}/{formaPagamento}")
	public String finalizarPedido(@PathVariable("id") Long id, @PathVariable("formaPagamento") String formaPagamento) {
		
		try {
			pedidoService.finalizarPedido(id, formaPagamento);
			return "redirect:/pedido/pedidos-finalizados";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
		return "/pedido/carrinho-compra"; 
	}
	
	@PostMapping("/cancelar-pedido")
	public String cancelarPedido(@ModelAttribute("dtoPedidoCancelado") DTOPedidoCancelado dtoPedidoCancelado, BindingResult bingBindingResult) {
		
		try {
			pedidoService.cancelarPedido(dtoPedidoCancelado.getIdPedido(), dtoPedidoCancelado.getMotivo());
		} catch (PedidoException e) {
			System.out.println(e.getMessage());
		}
		
		return "redirect:/pedido/pedidos-finalizados";
	}
	
	@GetMapping("/pedidos-finalizados")
	public String pedidosFinalizados(@AuthenticationPrincipal Usuario usuario, Model model) {
		model.addAttribute("pedidos", pedidoService.pedidosFinalizados(usuario.getId()));
		model.addAttribute("dtoPedidoCancelado", new DTOPedidoCancelado());
		return "/pedido/pedidos-user";
	}
	
	@PostMapping("/adicionar-item")
	public String adicionarItemAoCarrinho(@ModelAttribute("dtoPedido") DTOPedido dtoPedido, @AuthenticationPrincipal Usuario usuario) throws Exception {
		
		if(usuario.getEndereco() == null) {
			return "redirect:/pedido/cadastrar-endereco";
		}
		
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
	
	@GetMapping("/deletar-item/{idItem}")
	public String deletarItemPedido(@PathVariable("idItem") Long idItem) {
		pedidoService.deletarItemPedido(idItem);
		return "redirect:/pedido/carrinho-compra";
	}
	
	@GetMapping("/forma-pagamento")
	public String formaPagamento(Model model) {
		List<FormaPagamento> formasPagamentosCadastradas = formaPagamentoService.listar();
		model.addAttribute("formasPagamentoCadastradas", formasPagamentosCadastradas);
		model.addAttribute("formaPagamento", new FormaPagamento());
		return "/pedido/forma-pagamento";
	}
	
	@PostMapping("/forma-pagamento")
	public String formaPagamentoSubmit(@ModelAttribute("formaPagamento") FormaPagamento formaPagamento) {
		formaPagamentoService.salvar(formaPagamento);
		return "redirect:/pedido/forma-pagamento";
	}
	
	@RequestMapping("/atualizar-forma-pagamento/{id}")
	public String formaPagamentoSubmit(@PathVariable Long id) {
		formaPagamentoService.atualizar(id);
		return "redirect:/pedido/forma-pagamento";
	}

}
