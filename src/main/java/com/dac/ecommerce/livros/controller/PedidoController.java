package com.dac.ecommerce.livros.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.dac.ecommerce.livros.exceptions.PedidoException;
import com.dac.ecommerce.livros.exceptions.UsuarioException;
import com.dac.ecommerce.livros.model.user.Endereco;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.services.PedidoService;
import com.dac.ecommerce.livros.services.UsuarioService;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired private PedidoService pedidoService;
	@Autowired private UsuarioService usuarioService;
	
	@GetMapping("/carrinho-compras")
	public String carrinhoCompras(@AuthenticationPrincipal Usuario usuario, Model model) {
		return "";
	}
	
	@PostMapping("/fechar-pedido")
	public String fecharPedido(@AuthenticationPrincipal Usuario usuario) {
		return "";
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

}
