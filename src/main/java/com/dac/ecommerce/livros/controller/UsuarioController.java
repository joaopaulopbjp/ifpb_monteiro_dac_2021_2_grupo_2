package com.dac.ecommerce.livros.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.dac.ecommerce.livros.config.jwt.JwtUtil;
import com.dac.ecommerce.livros.dto.DTOAuth;
import com.dac.ecommerce.livros.dto.DTOUsuario;
import com.dac.ecommerce.livros.exceptions.UsuarioException;
import com.dac.ecommerce.livros.model.user.UserCredentials;
import com.dac.ecommerce.livros.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/user")
public class UsuarioController {
	
	@Autowired private UsuarioService usuarioService;
	@Autowired private JwtUtil jwtUtil;
	@Autowired private ObjectMapper mapper;
	@Autowired private AuthenticationManager authenticationManager;

	@PostMapping("/autenticar")
	public String autenticar(@RequestBody UserCredentials userCredentials) throws Exception {
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));
		} catch(Exception error) {
			throw new UsuarioException("CREDENCIAIS INVÁLIDAS!");
		}
		
		
		String profile = usuarioService.buscarPerfil(userCredentials.getUsername());
		String token = jwtUtil.gerarToken(userCredentials.getUsername());
		
		return mapper.writeValueAsString(new DTOAuth(token, profile));
	}
	
	@PostMapping("/cadastrar") 
	public void adicionarUsuario(@Valid @RequestBody DTOUsuario usuario, BindingResult bindingResult, Model model) throws UsuarioException {
		
		if(!bindingResult.hasErrors()) {
			usuarioService.cadastrarUsuario(usuario);
		}
		
		throw new UsuarioException("NÃO FOI POSSÍVEL CADASTRAR O USUÁRIO");
	}
	
}
