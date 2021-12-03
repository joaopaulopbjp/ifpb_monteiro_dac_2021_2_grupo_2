package com.dac.ecommerce.livros.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.dto.DTOUsuario;
import com.dac.ecommerce.livros.exceptions.UsuarioException;
import com.dac.ecommerce.livros.model.user.Role;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.repository.RoleRepository;
import com.dac.ecommerce.livros.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	

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
	public void cadastrarUsuario(DTOUsuario usuarioDTO) {
		
		Usuario usuario = usuarioDTO.parser();
		
		if(repository.findByEmail(usuario.getEmail()) == null) {
			Role role = new Role();
			
			switch(usuario.getTipoUsuario()) {
			case ADMINISTRADOR:
				role.setRole("ADMIN");
				break;
			case CLIENTE:
				role.setRole("CLIENTE");
				break;
			}
			
			roleRepository.save(role);
			
			Set<Role> roles = new HashSet<Role>();
			roles.add(role);
		
			usuario.setRoles(roles);
			
			repository.save(usuario);
		}
		
	
	}

}
