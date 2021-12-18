package com.dac.ecommerce.livros.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dac.ecommerce.livros.exceptions.EnderecoException;
import com.dac.ecommerce.livros.model.user.Endereco;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.services.EnderecoService;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
	
	@Autowired
	private EnderecoService enderecoService;

	@PostMapping("/cadastrar")
	public void cadastrar(@Valid @RequestBody Endereco endereco, @AuthenticationPrincipal Usuario usuario, BindingResult bindingResult) throws EnderecoException {
		
		if(bindingResult.hasErrors()) {
			throw new EnderecoException("NÃO FOI POSSÍVEL CADASTRAR O ENDEREÇO!");
		}
		
		enderecoService.salvar(usuario, endereco);
		
	}
	
	@PutMapping("/atualizar") 
	public void atualizar(@Valid @RequestBody Endereco endereco, @AuthenticationPrincipal Usuario usuario,  BindingResult bindingResult) throws EnderecoException {
		
		if(bindingResult.hasErrors()) {
			throw new EnderecoException("NÃO FOI POSSÍVEL ATUALIZAR O ENDEREÇO!");
		}
		
		enderecoService.atualizar(usuario, endereco);
		
	}
	
	@DeleteMapping("/deletar")
	public void deletar(@AuthenticationPrincipal Usuario usuario) throws EnderecoException {
		enderecoService.deletar(usuario);
	}
	
	@GetMapping("/consultar")
	public Endereco consultar(@AuthenticationPrincipal Usuario usuario) throws EnderecoException {
		Endereco endereco = enderecoService.consultar(usuario);
		
		if(endereco == null) {
			throw new EnderecoException("NÃO EXISTE ENDEREÇO CADASTRADO!");
		}
		
		return endereco;
	}
	
	
}
