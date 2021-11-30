package com.dac.ecommerce.livros.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dac.ecommerce.livros.exceptions.UsuarioException;
import com.dac.ecommerce.livros.model.user.Endereco;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.services.UsuarioService;


@Controller
@RequestMapping("/user")
public class UsuarioController {
	
	@Autowired private UsuarioService usuarioService;

	@GetMapping("/menu-conta")
	public String menuConta() {
		return "/user/menu-conta";
	}
	
	@GetMapping("/endereco-entrega")
	public String enderecoEntregaForm(Model model) {
		model.addAttribute("endereco", new Endereco());
		return "/pedido/cadastrar-endereco";
	}
	
	@PostMapping("/endereco-entrega")
	public String cadastrarEnderecoEntregaSubmit(@Valid @ModelAttribute("endereco") Endereco endereco, Model model, BindingResult bindingResult) throws UsuarioException {
		
		Usuario usuario = usuarioService.findById(1L);
		
		if(!bindingResult.hasErrors()) {
			usuario.setEndereco(endereco);
			usuarioService.save(usuario);
			
			return "/pedido/cadastrar-endereco";
		}

		return "/pedido/cadastrar-endereco";
			
	}
	
	
	
}
