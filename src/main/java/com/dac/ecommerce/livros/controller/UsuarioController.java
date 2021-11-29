package com.dac.ecommerce.livros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.services.UsuarioService;


@Controller
@RequestMapping("/user")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/cadastrar-usuario") 
	public String cadastrarUsuario(Model model) {
		
		model.addAttribute("usuario", new Usuario());
		return "usuario/login_user";
		
	}
	
	@PostMapping("/adicionar-usuario") 
	public String cadastrarUsuario(@ModelAttribute("usuario") Usuario usuario, BindingResult result, Model modelo) throws Exception {
		
		if(!result.hasErrors()) {
			
			usuarioService.save(usuario);
			
			System.out.println("Post: cadastrado com sucesso");

			return "redirect:/home";
		}
		
		return "/usuario/login_user";
		
	}

	@GetMapping("/menu-conta")
	public String menuConta() {
		return "/user/menu-conta";
	}
	
	@GetMapping("/endereco-entrega")
	public String enderecoEntrega() {
		return "/pedido/cadastrar-endereco";
	}
	
}
