package com.dac.ecommerce.livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dac.ecommerce.livros.model.estoque.HistoricoEstoque;

@Repository
public interface HistoricoEstoqueRepository extends JpaRepository<HistoricoEstoque, Long>{

}
