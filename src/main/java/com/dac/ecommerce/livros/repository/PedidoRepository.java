package com.dac.ecommerce.livros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dac.ecommerce.livros.model.pedido.ItemPedido;
import com.dac.ecommerce.livros.model.pedido.Pedido;

@Repository
@Transactional
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	// Seleciona todos os itens de um pedido
	@Query("SELECT itens FROM Pedido pedido JOIN ItemPedido itens ON pedido.id = itens.pedido_fk WHERE pedido.id = ?1")
	List<ItemPedido> findCarrinhoDeCompras(Long idProduto);
	
	// Seleciona todos os pedidos concluídos de um cliente
	@Query("SELECT pedido FROM Usuario cliente JOIN Pedido pedido ON pedido.cliente = cliente.id WHERE cliente.id = ?1 AND pedido.status = 'FINALIZADO' AND cliente.tipoUsuario = 'CLIENTE'")
	List<Pedido> findPedidosConcluidos(Long idCliente);

	// Selecionar pedidos não finalizados do cliente
	@Query("SELECT p FROM Pedido p WHERE p.status = 'NAO_FINALIZADO' and p.cliente.id = ?1")
	Pedido findPedidoNaoFinalizado(Long idCliente);

}
