package com.dac.ecommerce.livros.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dac.ecommerce.livros.model.estoque.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long>{

	
	
}
