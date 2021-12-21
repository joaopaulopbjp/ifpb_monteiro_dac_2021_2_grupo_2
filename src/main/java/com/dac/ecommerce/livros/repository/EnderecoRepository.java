package com.dac.ecommerce.livros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dac.ecommerce.livros.model.user.Endereco;
import com.dac.ecommerce.livros.model.user.Usuario;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	List<Endereco> findByUsuario(Usuario usuario);

}
