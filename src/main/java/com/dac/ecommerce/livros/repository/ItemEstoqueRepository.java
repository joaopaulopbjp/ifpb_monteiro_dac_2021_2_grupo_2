package com.dac.ecommerce.livros.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Livro;

@Repository
@Transactional
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long>{
	
	ItemEstoque findByProduto(Livro livro);
	
	// Ordenar todos os livros pelo preço
	@Query("SELECT p FROM ItemEstoque p ORDER BY p.preco ASC")
	List<ItemEstoque> ordenarItensDoEstoquePeloPreco();
	
	// Selecionar todos os livros presente no estoque
	@Query("SELECT l FROM Livro l JOIN ItemEstoque i ON l.id = i.produto")
	Page<Livro> consultarTodosLivrosPaginado(Pageable pageable);

	// Selecionar todos os livros com quantidade maior ou igual a 1
	@Query("SELECT i FROM ItemEstoque i WHERE i.quantidade > 0")
	List<ItemEstoque> consultarTodosLivrosDisponiveis();
	
	@Query("SELECT i FROM ItemEstoque i JOIN Livro l ON i.id = l.id WHERE l.titulo = ?1")
	ItemEstoque findLivroDisponivel(String titulo);
	
	@Query("SELECT i FROM ItemEstoque i JOIN Livro l ON i.produto = l.id JOIN Categoria c ON c.id = l.categoria WHERE c.nome = ?1")
	List<ItemEstoque> findByCategoria(String nome);
	
}
