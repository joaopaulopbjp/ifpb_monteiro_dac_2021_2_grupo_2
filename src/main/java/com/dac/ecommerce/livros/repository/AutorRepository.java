package com.dac.ecommerce.livros.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dac.ecommerce.livros.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long>{
	
	

}
