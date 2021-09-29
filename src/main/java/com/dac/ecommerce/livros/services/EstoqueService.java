package com.dac.ecommerce.livros.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.Estoque;
import com.dac.ecommerce.livros.model.EstoqueSingleton;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.model.PedidoItem;
import com.dac.ecommerce.livros.repository.EstoqueRepository;
import com.dac.ecommerce.livros.repository.LivroRepository;

@Service
public class EstoqueService {
	
	@Autowired
	private EstoqueRepository repository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	//adicionar no estoque atual
	public void adicionarNoEstoque(PedidoItem livro) throws Exception{

		List<Livro> livrosCadastrados = livroRepository.findAll();
		if(livrosCadastrados == null) {
			throw new Exception("Não existe Livros Cadastrados!");
		}
		
		Livro pesquisarLivro = livroRepository.getById(livro.getProduto().getIsbn());
		if(pesquisarLivro == null) {
			throw new Exception("Esse livro não está cadastrado!");
		}

		Estoque estoque = EstoqueSingleton.getEstoque();
		List<PedidoItem> lista = estoque.getItensNoEstoque();
		lista.add(livro);
	}
}
