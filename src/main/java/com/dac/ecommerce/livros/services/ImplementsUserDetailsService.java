package com.dac.ecommerce.livros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.repository.UsuarioRepository;
import com.dac.ecommerce.livros.services.ImplementsUserDetailsService;

@Service
public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO!");
		}

		return usuario;
	}

}
