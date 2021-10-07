package com.dac.ecommerce.livros.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Livro;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long>{
	
	ItemEstoque findByProduto(Livro livro);
	
}
