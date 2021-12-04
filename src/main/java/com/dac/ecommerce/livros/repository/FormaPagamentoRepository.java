package com.dac.ecommerce.livros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dac.ecommerce.livros.model.pedido.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
	FormaPagamento findByTipo(String tipo);

	@Query("SELECT f FROM FormaPagamento f WHERE f.isActive = true")
	List<FormaPagamento> findByActive();

}
