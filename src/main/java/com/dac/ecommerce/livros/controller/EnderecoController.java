package com.dac.ecommerce.livros.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.dac.ecommerce.livros.dto.DTOEndereco;
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
	public void atualizar(@Valid @RequestBody Endereco endereco, @AuthenticationPrincipal Usuario usuario, BindingResult bindingResult) throws EnderecoException {
		
		if(bindingResult.hasErrors()) {
			throw new EnderecoException("NÃO FOI POSSÍVEL ATUALIZAR O ENDEREÇO!");
		}
		
		enderecoService.atualizar(endereco);
	}
	
	@DeleteMapping("/deletar/{id}")
	public void deletar(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) throws EnderecoException {
		enderecoService.deletar(usuario, id);
	}
	
	@GetMapping("/consultar")
	public List<DTOEndereco> consultar(@AuthenticationPrincipal Usuario usuario) throws EnderecoException {
		
		List<DTOEndereco> enderecos = enderecoService.consultar(usuario);
		
		if(enderecos.size() == 0) {
			throw new EnderecoException("NÃO EXISTE ENDEREÇO CADASTRADO!");
		}

		return enderecos;
	}
	
	
}
