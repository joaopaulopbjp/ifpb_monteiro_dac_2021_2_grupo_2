package com.dac.ecommerce.livros.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.dto.DTOEndereco;
import com.dac.ecommerce.livros.exceptions.EnderecoException;
import com.dac.ecommerce.livros.model.user.Endereco;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.repository.EnderecoRepository;
import com.dac.ecommerce.livros.repository.UsuarioRepository;

@Service
public class EnderecoService {
	
	@Autowired private EnderecoRepository enderecoRepository;
	@Autowired private UsuarioRepository usuarioRepository;
	
	public void salvar(Usuario usuario, Endereco endereco) throws EnderecoException {
		
		for(Endereco enderecoRegistrado : usuario.getEnderecos()) {
			if(enderecoRegistrado.equals(endereco)) {
				throw new EnderecoException("ENDEREÇO JÁ CADASTRADO!");
			}
		}
		
		usuario.getEnderecos().add(endereco);
		endereco.setUsuario(usuario);
		usuarioRepository.save(usuario);
	}
	
	public void atualizar(Endereco endereco) {
		Endereco enderecoDesatualizado = enderecoRepository.findById(endereco.getId()).get();
		BeanUtils.copyProperties(enderecoDesatualizado, endereco, "id");
		enderecoRepository.save(enderecoDesatualizado);
	}
	
	public void deletar(Usuario usuario, Long idEndereco) throws EnderecoException {
		
		Endereco endereco = enderecoRepository.findById(idEndereco).get();
		
		usuario.getEnderecos().remove(endereco);
		
		endereco.setUsuario(null);
		
		usuarioRepository.save(usuario);
		enderecoRepository.deleteById(endereco.getId());
	}
	
	public List<DTOEndereco> consultar(Usuario usuario) {
		List<Endereco> enderecos =  enderecoRepository.findByUsuario(usuario);
		List<DTOEndereco> DTOenderecos = new ArrayList<DTOEndereco>();
		
		enderecos.forEach((e) -> {
			DTOenderecos.add(new DTOEndereco(e));
		});
		
		return DTOenderecos;
	}

}
