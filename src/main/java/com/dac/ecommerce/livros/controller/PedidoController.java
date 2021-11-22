package com.dac.ecommerce.livros.controller;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.dac.ecommerce.livros.model.user.Endereco;

@Controller
@RequestMapping("/pedido")
public class PedidoController {
	
	

	@PostMapping("/cadastrar-endereco-entrega")
	public String cadastrarEnderecoEntrega(@ModelAttribute("endereco") Endereco endereco, Model modelo) {
		return "redirect:/pedido/home";
	}

}
