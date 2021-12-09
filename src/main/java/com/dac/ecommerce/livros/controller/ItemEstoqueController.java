package com.dac.ecommerce.livros.controller;

import java.util.Calendar;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dac.ecommerce.livros.dto.DTOItemEstoque;
import com.dac.ecommerce.livros.model.estoque.EstadoItem;
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
	
	@GetMapping("/form-adicionar-item/{id}")
	public String form(@PathVariable("id") Long id, Model model) {
		Livro livro = livroService.buscarLivro(id);
		DTOItemEstoque dtoItemEstoque = new DTOItemEstoque();
		dtoItemEstoque.setIdlivro(livro.getId());
		dtoItemEstoque.setPrecoItem(livro.getPreco());
		model.addAttribute("dtoItem",dtoItemEstoque);
		model.addAttribute("estoques",estoqueService.listarEstoques());
		return "/estoque/livro-estoque";
	}
	
	

	@PostMapping("/salvar")
	public String salvar(@Valid @ModelAttribute("dtoItem")DTOItemEstoque dtoItemEstoque,BindingResult bindingResult,
			RedirectAttributes atts,Model model) {
		
		if (!bindingResult.hasErrors()) {
			ItemEstoque itemEstoque = dtoItemEstoque.toItemEstoque();
			Long id = dtoItemEstoque.getIdlivro();
			Livro livro = livroService.buscarLivro(id);
			itemEstoque.setProduto(livro);
			livro.setAdicionadoEmEstoque(true);
			
			HistoricoEstoque historicoEstoque = new HistoricoEstoque();
			historicoEstoque.setEstadoItem(EstadoItem.CRIADO);
			Calendar dataAtual = Calendar.getInstance();
			historicoEstoque.setCalendario(dataAtual);
			historicoEstoque.setNomeItem(itemEstoque.getProduto().getTitulo());
			
			itemEstoque.setHistoricoEstoque(historicoEstoque);
			itemEstoqueService.salvarItem(itemEstoque);
			return "redirect:/estoque/itens-estoque";
		}
		
		model.addAttribute("estoques",estoqueService.listarEstoques());
		atts.addAttribute("hasErrors", true);
		return "/estoque/livro-estoque";
		
	}

	@GetMapping("/adicionar-itens/{id}")
	public String adicionarItens(@PathVariable("id") Long id) {
		ItemEstoque itemEstoque = itemEstoqueService.bucarItem(id);
		itemEstoqueService.adicionarItem(itemEstoque.getQuantidade(), itemEstoque.getId());
		return "redirect:/estoque/itens-estoque";
	}

	@GetMapping("/remover-item/{id}")
	public String removerItens(@PathVariable("id") Long id) {
		ItemEstoque itemEstoque = itemEstoqueService.bucarItem(id);
		itemEstoqueService.removerItem(itemEstoque.getQuantidade(), itemEstoque.getId());
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
