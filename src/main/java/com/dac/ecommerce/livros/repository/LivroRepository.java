package com.dac.ecommerce.livros.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dac.ecommerce.livros.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, String>{

}
