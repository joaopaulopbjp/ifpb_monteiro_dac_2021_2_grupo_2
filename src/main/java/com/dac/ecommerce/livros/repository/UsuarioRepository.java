package com.dac.ecommerce.livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dac.ecommerce.livros.model.user.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByEmail(String email);
	public Usuario findByNome(String nome);
	public Usuario findByCpf(String cpf);
}
