package com.dac.ecommerce.livros.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dac.ecommerce.livros.model.livro.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	Categoria findByNome(String nome);
}
