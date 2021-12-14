package com.dac.ecommerce.livros.services;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.exceptions.LivroException;
import com.dac.ecommerce.livros.model.estoque.ItemEstoque;
import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.model.livro.Livro;
import com.dac.ecommerce.livros.repository.ItemEstoqueRepository;
import com.dac.ecommerce.livros.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repositorioLivro;

	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;

	public void salvar(Livro livro) throws Exception {
		repositorioLivro.save(livro);
	}

	public void excluirLivro(String isbn) throws LivroException {
		Livro livro = repositorioLivro.findByIsbn(isbn);
		if (livro == null) {
			throw new LivroException("[ERROR] - O LIVRO NÃO CADASTRADO!");
		}
		ItemEstoque item = itemEstoqueRepository.findByProduto(livro);
		if (item != null) {
			throw new LivroException("[ERROR] - LIVRO CADASTRADO NO ESTOQUE!!");
		}
		repositorioLivro.delete(livro);
	}

	public void excluir(Long id) {
		repositorioLivro.deleteById(id);
	}

	public Livro buscarLivro(Long id) {
		Livro livro = repositorioLivro.findById(id).get();
		return livro;
	}

	public List<Livro> recuperarTodosOsLivros() {
		List<Livro> livros = repositorioLivro.findAll();
		return livros;
	}

	public Livro bucarLivroPeloIsbn(String isbn) throws Exception {
		Livro verificarISBN = repositorioLivro.findByIsbn(isbn);
		if (verificarISBN == null) {
			throw new Exception("[ERROR LIVRO] - LIVRO NÃO ENCONTRADO!");
		}
		return verificarISBN;
	}

	public void alterarLivro(Livro livro, Long id) {
		Livro bucarLivro = repositorioLivro.findById(id).get();
		BeanUtils.copyProperties(livro, bucarLivro);
		repositorioLivro.save(bucarLivro);
	}

	public Livro bucarPelaEditora(Editora editora) {
		return repositorioLivro.findByEditora(editora);
	}

	public Livro bucarPelaCategoria(Categoria categoria) {
		return repositorioLivro.findByCategoria(categoria);
	}

	public Page<Livro> pageLivros(int pageNumber) {
		Page<Livro> page = repositorioLivro.findAll(PageRequest.of(pageNumber - 1, 5, Sort.by("titulo").ascending()));
		return page;
	}
}
