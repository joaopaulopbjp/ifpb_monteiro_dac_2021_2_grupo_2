package com.dac.ecommerce.livros.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dac.ecommerce.livros.model.ItemEstoque;
import com.dac.ecommerce.livros.model.Livro;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long>{
	
	ItemEstoque findByProduto(Livro livro);
	
}
