package com.dac.ecommerce.livros.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.exceptions.UsuarioException;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.model.user.UsuarioDTO;
import com.dac.ecommerce.livros.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	
	//Salva o usuário no Banco de Dados
	public Usuario save(Usuario usuario) {
		return repository.save(usuario);
	}

	//Atualiza o usuário. É feita a busca pelo id do cliente informado fazendo uma
	//cópia das suas informações pelo BeanUtils
	public Usuario update(Usuario usuario) {
		Usuario usuarioSalvo = repository.findById(usuario.getId()).get();
		BeanUtils.copyProperties(usuario, usuarioSalvo,"id");
		repository.save(usuarioSalvo);
		return usuarioSalvo;	
		
	}
	
	//Método deleta um usuário do Banco
	public void delete(long id) {
		repository.deleteById(id);
	}
	
	//Método busca um usuário pelo ID
	public Usuario findById(long id) throws UsuarioException {
		Usuario usuario = repository.findById(id).get();
		
		if(usuario == null) {
			throw new UsuarioException("[ERROR USUÁRIO] - USUÁRIO NÃO ENCONTRADO!");
		}
		
		return usuario;
	}
	
	//Método Busca usuário pelo nome
	public Usuario findByNome(String nome) {
		return repository.findByNome(nome);
	}


	public Page<Usuario> findAll(Pageable page) {
		return repository.findAll(page);
	}

	//Método busca usuário pelo Email
	public Usuario findByEmail(String email) throws UsuarioException {
		Usuario usuario = repository.findByEmail(email);
		
		if(usuario == null) {
			throw new UsuarioException("[ERROR USUÁRIO] - USUÁRIO NÃO ENCONTRADO!");
		}
		
		return usuario;
	}
	
	// metodo para cadastrar usuario
			public void cadastrarUsuario(UsuarioDTO usuarioDTO) {

				Usuario usuario = usuarioDTO.parser();

				repository.save(usuario);
			}

}
