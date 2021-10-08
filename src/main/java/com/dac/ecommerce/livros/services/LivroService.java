package com.dac.ecommerce.livros.services;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.exceptions.LivroAutorException;
import com.dac.ecommerce.livros.exceptions.LivroCategoriaException;
import com.dac.ecommerce.livros.exceptions.LivroException;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.repository.AutorRepository;
import com.dac.ecommerce.livros.repository.ItemEstoqueRepository;
import com.dac.ecommerce.livros.repository.LivroRepository;

@Service
public class LivroService {
	
	@Autowired
	private LivroRepository repositorioLivro;
	
	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;
	
	@Autowired
	private AutorRepository autorRepository;
	
	public void salvarLivro(Integer autores, List<Long> idsAutores,
		String isbn, String categoria,String titulo,String descricao,
		BigDecimal preco, byte[] imagemCapa, String nomeDaEditora, 
		String cidadeEditora,Integer edicao,Integer ano) throws 
		LivroException,LivroAutorException, LivroCategoriaException{
		if(autores <= 0) {
			throw new LivroAutorException();
		}
		
		//pega os autores previamente cadastrados
		List<Autor> autoresLista = new ArrayList<>();
		for (int i = 0; i < idsAutores.size(); i++) {
			Autor a = autorRepository.findById(idsAutores.get(i)).get();
			autoresLista.add(a);
		}
		
		Livro livro = repositorioLivro.findByIsbn(isbn);
		if(livro != null) {
			throw new LivroException("[ERROR] LIVRO JÁ CADASTRADO");
		}
		if(categoria.equals("")) {
			throw new LivroCategoriaException();
		}
		Livro novoLivro = new Livro();
		novoLivro.setAutores(autoresLista);
		novoLivro.setIsbn(isbn);
		Editora editora = new Editora();
		editora.setNome(nomeDaEditora);
		editora.setCidade(cidadeEditora);
		novoLivro.setEditora(editora);
		Categoria c = new Categoria();
		c.setNome(categoria);
		novoLivro.setCategoria(c);
		novoLivro.setTitulo(titulo);
		novoLivro.setDescricao(descricao);
		novoLivro.setPreco(preco);
		novoLivro.setImagemCapa(imagemCapa);
		novoLivro.setEdicao(edicao);
		novoLivro.setAno(ano);
		repositorioLivro.save(novoLivro);	
	}
	
	public void excluirLivro(String isbn) throws LivroException{
		Livro livro = repositorioLivro.findByIsbn(isbn);
		if(livro == null) {
			throw new LivroException("[ERROR] - O LIVRO NÃO CADASTRADO!");
		}
		ItemEstoque item = itemEstoqueRepository.findByProduto(livro);
		if(item != null) {
			throw new LivroException("[ERROR] - LIVRO CADASTRADO NO ESTOQUE!!");
		}
		repositorioLivro.delete(livro);
	}
	
	public Livro buscarLivro(Long id) throws LivroException {
		Livro livro = repositorioLivro.findById(id).get();
		if(livro == null) {
			throw new LivroException("[ERROR] - O LIVRO NÃO FOI ENCONTRADO!");
		}
		return livro;
	}
	
	public List<Livro> recuperarTodosOsLivros() throws Exception{
		List<Livro> livros = repositorioLivro.findAll();
		if(livros.size() == 0) {
			throw new Exception("[ERROR LIVRO] - NÃO EXISTE LIVROS CADASTRADOS!");
		}
		return livros;
	}
	
	public Livro bucarLivroPeloIsbn(String isbn) throws Exception{
		Livro verificarISBN = repositorioLivro.findByIsbn(isbn);
		if(verificarISBN == null) {
			throw new Exception("[ERROR LIVRO] - LIVRO NÃO ENCONTRADO!");
		}
		return verificarISBN;
	}
	
	public void alterarValorDoLivro(String isbn, BigDecimal valor) throws Exception{
		Livro livro = repositorioLivro.findByIsbn(isbn);
		if(livro == null) {
			throw new Exception("[ERROR] LIVRO NÃO PODE SER ALTERADO");
		}
		livro.setPreco(valor);
		ItemEstoque item = itemEstoqueRepository.findByProduto(livro);
		if(item != null) {
			item.setPreco(valor);
			itemEstoqueRepository.save(item);
		}
		repositorioLivro.save(livro);
	}

}
