package com.dac.ecommerce.livros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dac.ecommerce.livros.exceptions.PedidoException;
import com.dac.ecommerce.livros.services.PedidoService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping("/carrinho-compras")
	public String carrinhoCompras(Model model) {
		return "";
	}
	
	@PostMapping("/fechar-pedido")
	public String fecharPedido() {
		return "";
	}
	
	@PostMapping("/cancelar-pedido")
	public String cancelarPedido() {
		return "";
	}
	
	@PostMapping("/endereco-entrega")
	public String cadastrarEnderecoEntrega() {
		return "";
	}
	
	@GetMapping("/pedidos-finalizados")
	public String pedidosFinalizados(Authentication authentication, Model model) throws PedidoException {
		System.out.println(authentication.getUsername());
		//model.addAttribute("pedidos", pedidoService.pedidosFinalizados(usuario.getId()));
		return "/pedido/pedidos-user";
	}

}
