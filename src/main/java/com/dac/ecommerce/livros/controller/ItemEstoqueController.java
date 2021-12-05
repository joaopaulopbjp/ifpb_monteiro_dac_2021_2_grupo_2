package com.dac.ecommerce.livros.controller;

import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dac.ecommerce.livros.dto.DTOItemEstoque;
import com.dac.ecommerce.livros.model.estoque.EstadoItem;
import com.dac.ecommerce.livros.model.estoque.Estoque;
import com.dac.ecommerce.livros.model.estoque.HistoricoEstoque;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.services.EstoqueService;
import com.dac.ecommerce.livros.services.HistoricoEstoqueService;
import com.dac.ecommerce.livros.services.ItemEstoqueService;
import com.dac.ecommerce.livros.services.LivroService;

@Controller
@RequestMapping("/item-estoque")
public class ItemEstoqueController {

	@Autowired
	private ItemEstoqueService itemEstoqueService;

	@Autowired
	private EstoqueService estoqueService;

	@Autowired
	private LivroService livroService;

	@Autowired
	private HistoricoEstoqueService historicoEstoqueService;

	@PostMapping("/salvar")
	public String salvar(DTOItemEstoque dtoItemEstoque) {

		ItemEstoque itemEstoque = dtoItemEstoque.toItemEstoque();
		Estoque estoque = estoqueService.bucarEstoque(dtoItemEstoque.getIdEstoqueItem());
		Livro livro = livroService.buscarLivro(dtoItemEstoque.getIdLivroItem());
		livro.setAdicionadoEmEstoque(true);
		livroService.alterarLivro(livro, livro.getId());
		
		itemEstoque.setEstoque(estoque);
		itemEstoque.setProduto(livro);

		HistoricoEstoque historicoEstoque = new HistoricoEstoque();
		historicoEstoque.setEstadoItem(EstadoItem.CRIADO);
		Calendar dataAtual = Calendar.getInstance();
		historicoEstoque.setCalendario(dataAtual);
		historicoEstoque.setNomeItem(itemEstoque.getProduto().getTitulo());
		
		itemEstoque.setHistoricoEstoque(historicoEstoque);
		
		itemEstoqueService.salvarItem(itemEstoque);
		return "redirect:/estoque/itens-estoque";
	}

	@PostMapping("/adicionar-itens")
	public String adicionarItens(DTOItemEstoque dtoItemEstoque) {
		itemEstoqueService.adicionarItem(dtoItemEstoque.getQtd(), dtoItemEstoque.getIdItem());
		return "redirect:/estoque/itens-estoque";
	}

	@PostMapping("/remover-itens")
	public String removerItens(DTOItemEstoque dtoItemEstoque) {
		itemEstoqueService.removerItem(dtoItemEstoque.getQtd(), dtoItemEstoque.getIdItem());
		return "redirect:/estoque/itens-estoque";
	}

	@GetMapping("/deletar-item/{id}")
	public String deletar(@PathVariable("id") Long id) {

		HistoricoEstoque historicoEstoque = itemEstoqueService.bucarItem(id).getHistoricoEstoque();
		HistoricoEstoque historicoAtual = new HistoricoEstoque();
		Calendar dataAtual = Calendar.getInstance();
		historicoAtual.setCalendario(dataAtual);
		historicoAtual.setEstadoItem(EstadoItem.EXCLUIDO);
		historicoAtual.setNomeItem(historicoEstoque.getNomeItem());
		historicoEstoqueService.salvar(historicoAtual);

		itemEstoqueService.deletar(id);

		return "redirect:/estoque/itens-estoque";
	}
}
