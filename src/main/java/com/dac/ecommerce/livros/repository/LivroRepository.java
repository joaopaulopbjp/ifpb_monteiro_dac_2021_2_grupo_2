package com.dac.ecommerce.livros.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dac.ecommerce.livros.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{

	public Livro findByIsbn(String isbn);
}
