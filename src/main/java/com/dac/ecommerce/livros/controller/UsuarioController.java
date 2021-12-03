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
import com.dac.ecommerce.livros.model.user.UsuarioDTO;
import com.dac.ecommerce.livros.services.UsuarioService;


@Controller
@RequestMapping("/user")
public class UsuarioController {
	

	@Autowired private UsuarioService usuarioService;
	
	@RequestMapping("/login-usuario")
	public String mostrarLogin(UsuarioDTO usuarioDTO,  Model model) {
		//model.addAttribute("usuarioDTO",new UsuarioDTO());
		return "/user/login-user";
	}
	
	@RequestMapping("/cadastrar-usuario") 
	public String cadastrarUsuario(UsuarioDTO usuarioDTO,  Model model) {
		
		//model.addAttribute("usuario", new Usuario());
		return "/user/cadastrar-user";
		
	}
	
	
	@PostMapping("/login-usuario")
	public String login(UsuarioDTO usuarioDTO, Model model) throws UsuarioException {
		System.out.println(usuarioDTO.getEmail());
		
		if(usuarioService.findByEmail(usuarioDTO.getEmail()) == null) {
			return "redirect:/login";
		}
		return "redirect:/home";
	}
	
	@PostMapping("/adicionar-usuario") 
	public String adicionarUsuario(UsuarioDTO usuarioDTO, Model model) throws Exception {
		
		System.out.println(usuarioDTO.getEmail());
		
		usuarioService.cadastrarUsuario(usuarioDTO);
		
		return "redirect:/user/login-usuario";
		
	}
	
	@PostMapping("/endereco-entrega")
	public String cadastrarEnderecoEntregaSubmit(@Valid @ModelAttribute("endereco") Endereco endereco, BindingResult bindingResult, Model model) {
		
		try {
			Usuario usuario = usuarioService.findById(1L);
			
			if(!bindingResult.hasErrors()) {
				usuario.setEndereco(endereco);
				usuarioService.save(usuario);
				
				Thread.sleep(3000);
				return "redirect:/user/menu-conta";
			}
		} catch(Exception error) {
			System.out.println(error.getMessage());
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
