package com.dac.ecommerce.livros.services;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.exceptions.LivroAutorException;
import com.dac.ecommerce.livros.exceptions.LivroCategoriaException;
import com.dac.ecommerce.livros.exceptions.LivroException;
import com.dac.ecommerce.livros.exceptions.PaginaInvalidaException;
import com.dac.ecommerce.livros.model.Autor;
import com.dac.ecommerce.livros.model.Categoria;
import com.dac.ecommerce.livros.model.ItemEstoque;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.repository.ItemEstoqueRepository;
import com.dac.ecommerce.livros.repository.LivroRepository;

@Service
public class LivroService {
	
	@Autowired
	private LivroRepository repositorioLivro;
	
	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;
	
	public void salvarLivro(List<Autor> autores, String isbn, String categoria,
		String titulo,String descricao, BigDecimal preco, byte[] imagemCapa,
		Integer edicao, Integer ano) throws LivroException,LivroAutorException, 
		LivroCategoriaException{
		if(autores.size() == 0) {
			throw new LivroAutorException();
		}
		Livro livro = repositorioLivro.findByIsbn(isbn);
		if(livro != null) {
			throw new LivroException("[ERROR] LIVRO JÁ CADASTRADO");
		}
		if(categoria.equals("")) {
			throw new LivroCategoriaException();
		}
		Livro novoLivro = new Livro();
		novoLivro.setAutores(autores);
		novoLivro.setIsbn(isbn);
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
	
	public String excluirLivro(String isbn) throws LivroException{
		try {
			Livro livro = bucarLivroPeloIsbn(isbn);
			ItemEstoque item = itemEstoqueRepository.findByProduto(livro);
			if(item != null) {
				throw new LivroException("[ERROR] - LIVRO CADASTRADO NO ESTOQUE!!");
//				itemEstoqueRepository.delete(item);
			}
			repositorioLivro.delete(livro);
			return "LIVRO EXCLUÍDO COM SUCESSO!";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "[ERROR] - NÃO FOI POSSÍVEL EXCLUÍR O LIVRO!!";
	}
	
	public Livro buscarLivro(Long id) throws LivroException {
		Livro livro = repositorioLivro.findById(id).get();
		if(livro == null) {
			throw new LivroException("[ERROR LIVRO] - O LIVRO NÃO FOI ENCONTRADO!");
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
			throw new Exception("[ERROR LIVRO] - LIVRO NÃO CADASTRADO!");
		}
		return verificarISBN;
	}
	
	public void alterarLivro(Livro livro) {
		try {
			ItemEstoque item = itemEstoqueRepository.findByProduto(livro);
			if(item != null) {
				itemEstoqueRepository.save(item);
			}
			repositorioLivro.save(livro);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String listarLivrosPorPaginacao(Integer numeroPagina) throws PaginaInvalidaException {
		Pageable pageable = PageRequest.of((numeroPagina - 1), 5, Sort.by("titulo").ascending());
		Page<Livro> pagina = repositorioLivro.findAll(pageable);
		
		if(pagina.isEmpty()) {
			throw new PaginaInvalidaException();
		}
		
		String livros = "";
		for(Livro livro : pagina) {
			livros += livro.toString();
		}
				
		return livros;
	}
}
