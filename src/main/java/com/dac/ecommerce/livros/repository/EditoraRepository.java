package com.dac.ecommerce.livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dac.ecommerce.livros.model.livro.Editora;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {

	Editora findByNome(String nome);
	
}
