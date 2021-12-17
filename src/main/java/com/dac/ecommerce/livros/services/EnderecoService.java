package com.dac.ecommerce.livros.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.EnderecoException;
import com.dac.ecommerce.livros.model.user.Endereco;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.repository.UsuarioRepository;

@Service
public class EnderecoService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void salvar(Usuario usuario, Endereco endereco) {
		usuario.setEndereco(endereco);
		usuarioRepository.save(usuario);
	}
	
	public void atualizar(Usuario usuario, Endereco endereco) {
		Endereco enderecoAntigo = usuario.getEndereco();
		BeanUtils.copyProperties(endereco, enderecoAntigo);
		usuario.setEndereco(endereco);
		usuarioRepository.save(usuario);
	}
	
	public void deletar(Usuario usuario) throws EnderecoException {
		
		if(usuario.getEndereco() == null) {
			throw new EnderecoException("NÃO EXISTE ENDEREÇO CADASTRADO!");
		}
		
		usuario.setEndereco(null);
		usuarioRepository.save(usuario);
	}
	
	public Endereco consultar(Usuario usuario) {
		return usuario.getEndereco();
	}

}
