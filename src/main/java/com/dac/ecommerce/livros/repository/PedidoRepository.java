package com.dac.ecommerce.livros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dac.ecommerce.livros.model.pedido.ItemPedido;
import com.dac.ecommerce.livros.model.pedido.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	// Seleciona todos os itens de um pedido
	@Query("SELECT itens FROM Pedido pedido JOIN ItemPedido itens ON pedido.id = itens.pedido_fk WHERE pedido.id = ?1")
	List<ItemPedido> findCarrinhoDeCompras(Long idProduto);
	
	@Query("SELECT pedido FROM Cliente cliente JOIN Pedido pedido ON pedido.cliente_fk = cliente.id WHERE cliente.id = ?1 AND pedido.status = 'FINALIZADO'")
	List<Pedido> findPedidosConcluidos(Long idCliente);

}
