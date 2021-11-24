package com.dac.ecommerce.livros.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UsuarioController {

	@GetMapping("/menu-conta")
	public String menuConta() {
		return "/user/menu-conta";
	}
	
	@GetMapping("/endereco-entrega")
	public String enderecoEntrega() {
		return "/pedido/cadastrar-endereco";
	}
	
}
