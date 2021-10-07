package com.dac.ecommerce.livros.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Livro;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long>{
	
	ItemEstoque findByProduto(Livro livro);
	
	//ordenar todos os livros pelo preço
	@Query("SELECT p FROM ItemEstoque p ORDER BY p.preco ASC")
	List<ItemEstoque> ordenarItensDoEstoquePeloPreco();
	
}
