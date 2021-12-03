package com.dac.ecommerce.livros.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.dac.ecommerce.livros.config.ImplementsUserDetailsService;

import com.dac.ecommerce.livros.model.user.Usuario;
import com.dac.ecommerce.livros.repository.UsuarioRepository;

public class ImplementsUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Usuario user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException("USUÁRIO NÃO ENCONTRADO!");
		}

		return user;
	}

}
