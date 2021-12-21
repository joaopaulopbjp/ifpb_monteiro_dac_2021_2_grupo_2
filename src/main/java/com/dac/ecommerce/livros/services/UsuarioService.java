package com.dac.ecommerce.livros.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.dto.DTOUsuario;
import com.dac.ecommerce.livros.exceptions.UsuarioException;
import com.dac.ecommerce.livros.model.user.Role;
import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.repository.RoleRepository;
import com.dac.ecommerce.livros.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired private UsuarioRepository usuarioRepository;
	@Autowired private RoleRepository roleRepository;
	
	public Usuario update(Usuario usuario) {
		Usuario usuarioSalvo = usuarioRepository.findById(usuario.getId()).get();
		BeanUtils.copyProperties(usuario, usuarioSalvo,"id");
		usuarioRepository.save(usuarioSalvo);
		return usuarioSalvo;	
		
	}
	
	public void delete(long id) {
		usuarioRepository.deleteById(id);
	}
	
	public Usuario findById(long id) throws UsuarioException {
		Usuario usuario = usuarioRepository.findById(id).get();
		
		if(usuario == null) {
			throw new UsuarioException("[ERROR USUÁRIO] - USUÁRIO NÃO ENCONTRADO!");
		}
		
		return usuario;
	}
	
	public Usuario findByNome(String nome) {
		return usuarioRepository.findByNome(nome);
	}

	public String buscarPerfil(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		return new ArrayList<Role>(usuario.getRoles()).get(0).getRole();
	}

	public Page<Usuario> findAll(Pageable page) {
		return usuarioRepository.findAll(page);
	}

	public Usuario findByEmail(String email) throws UsuarioException {
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if(usuario == null) {
			throw new UsuarioException("[ERROR USUÁRIO] - USUÁRIO NÃO ENCONTRADO!");
		}
		
		return usuario;
	}
	
	public void cadastrarUsuario(DTOUsuario usuarioDTO) {
		
		Usuario usuario = usuarioDTO.parser();
		
		if(usuarioRepository.findByEmail(usuario.getEmail()) == null) {
			
			Role role = new Role();
			
			switch(usuario.getTipoUsuario()) {
			case ADMINISTRADOR:
				role.setRole("ADMIN");
				break;
			case CLIENTE:
				role.setRole("CLIENTE");
				break;
			}
			
			if(roleRepository.findByRole(role.getRole()) != null) {
				role = roleRepository.findByRole(role.getRole());
			} else {
				roleRepository.save(role);
			}
			
			Set<Role> roles = new HashSet<Role>();
			roles.add(role);
		
			usuario.setRoles(roles);
			usuarioRepository.save(usuario);
		}
		
	
	}

}
