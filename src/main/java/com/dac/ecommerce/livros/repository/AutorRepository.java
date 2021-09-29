package com.dac.ecommerce.livros.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dac.ecommerce.livros.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long>{
	
	public Autor findUniqueByNome(String nome);
	
	public List<Autor> findByNome(String nome);
	
	public Optional<Autor> findById(Long ID);
	
	public Autor findByID(long ID);
}
