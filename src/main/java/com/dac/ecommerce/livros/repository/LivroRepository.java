package com.dac.ecommerce.livros.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dac.ecommerce.livros.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{


}
