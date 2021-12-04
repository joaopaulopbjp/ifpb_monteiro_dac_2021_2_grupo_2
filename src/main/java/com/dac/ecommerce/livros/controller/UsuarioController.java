package com.dac.ecommerce.livros.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.dac.ecommerce.livros.dto.DTOUsuario;
import com.dac.ecommerce.livros.exceptions.UsuarioException;
import com.dac.ecommerce.livros.model.user.Endereco;

import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.services.UsuarioService;


@Controller
@RequestMapping("/user")
public class UsuarioController {
	
	@Autowired private UsuarioService usuarioService;

	@RequestMapping("/login")
	public String login() {
		return "/user/login-user";
	}

	@GetMapping("/cadastrar") 
	public String cadastrarUsuario(DTOUsuario usuarioDTO,  Model model) {
		model.addAttribute("usuarioDTO", new Usuario());
		return "/user/cadastrar-user";
	}
	
	@PostMapping("/cadastrar") 
	public String adicionarUsuario(@Valid @ModelAttribute("usuarioDTO") DTOUsuario usuarioDTO, BindingResult bindingResult, Model model) {
		
		if(!bindingResult.hasErrors()) {
			usuarioService.cadastrarUsuario(usuarioDTO);
			return "redirect:/user/login-user";
		}
		
		return "/user/cadastrar-user";
	}
	
	@GetMapping("/endereco-entrega")
	public String cadastrarEnderecoEntregaForm(Model model) {
		model.addAttribute("endereco", new Endereco());
		return "/pedido/cadastrar-endereco";
	}
	
	@PostMapping("/endereco-entrega")
	public String cadastrarEnderecoEntregaSubmit(@Valid @ModelAttribute("endereco") Endereco endereco, BindingResult bindingResult, @AuthenticationPrincipal Usuario usuario) {
		
		try {
			if(!bindingResult.hasErrors()) {
				usuario.setEndereco(endereco);
				usuarioService.update(usuario);
				
				Thread.sleep(3000);
				return "redirect:/user/menu-conta";
			}
		} catch(Exception error) {
			System.out.println(error.getMessage());
		}
		
		return "/pedido/cadastrar-endereco";
	}
	
	@GetMapping("/alterar-dados")
	public String atualizarDadosForm(@AuthenticationPrincipal Usuario usuario, Model model) throws UsuarioException {
		model.addAttribute("user", usuario);
		return "/user/alterar-dados";
	}
	
	@PostMapping("/alterar-dados")
	public String atualizarDadosSubmit(@Valid @ModelAttribute("user") Usuario usuario, Model model) throws UsuarioException, InterruptedException {

		usuarioService.update(usuario);
		
		Thread.sleep(3000);
		return "redirect:/user/alterar-dados";
	}
	
	@GetMapping("menu-conta")
	public String menuConta() {
		return "/user/menu-conta";
	}
	
}
