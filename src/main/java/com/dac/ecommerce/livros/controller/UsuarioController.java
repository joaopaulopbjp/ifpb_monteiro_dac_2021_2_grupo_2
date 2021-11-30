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
	
	@RequestMapping("/cadastrar-usuario") 
	public String cadastrarUsuario(Model model) {
		
		//model.addAttribute("usuario", new Usuario());
		return "/user/cadastrar-user";
		
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
	public String enderecoEntregaForm(Model model) {
		model.addAttribute("endereco", new Endereco());
		return "/pedido/cadastrar-endereco";
	}
	
	@PostMapping("/endereco-entrega")
	public String cadastrarEnderecoEntregaSubmit(@Valid @ModelAttribute("endereco") Endereco endereco, Model model, BindingResult bindingResult) throws UsuarioException, InterruptedException {
		
		Usuario usuario = usuarioService.findById(1L);
		
		if(!bindingResult.hasErrors()) {
			usuario.setEndereco(endereco);
			usuarioService.save(usuario);
			
			Thread.sleep(3000);
			return "/pedido/cadastrar-endereco";
		}

		return "/pedido/cadastrar-endereco";
	}
	
	@GetMapping("/alterar-dados")
	public String atualizarDadosForm(Model model) throws UsuarioException {
		
		Usuario usuario = usuarioService.findById(1L);
		System.out.println(usuario.getId());
		
		model.addAttribute("user", usuario);
		return "/user/alterar-dados";
	}
	
	@PostMapping("/alterar-dados")
	public String atualizarDadosForm(@Valid @ModelAttribute("user") Usuario usuario, Model model) throws UsuarioException, InterruptedException {

		usuarioService.update(usuario);
		
		Thread.sleep(3000);
		return "redirect:/user/alterar-dados";
	}
	
	
	
}
