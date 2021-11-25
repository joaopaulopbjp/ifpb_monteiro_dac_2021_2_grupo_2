package com.dac.ecommerce.livros.dto;

import java.math.BigDecimal;
import java.util.List;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.model.livro.Livro;

import lombok.Data;

@Data // (Data Transfer Object)
public class DTOLivro {

	private String isbn;
	private String titulo;
	private String descricao;
	private String preco;
	private String nomeEditora;
	private String cidadeEditora;
	private String nomeCategoria;
	private Integer edicao;
	private Integer ano;
	private List<Autor> autoresCadastrados;

	public Livro toLivro() {
		Livro livro = new Livro();
		Editora editora = new Editora();
		Categoria categoria = new Categoria();

		livro.setIsbn(this.isbn);
		livro.setTitulo(this.titulo);
		livro.setDescricao(this.descricao);
		livro.setPreco(new BigDecimal(this.preco.replace(",", ".")));
		// livro.setImagemCapa(this.imagemCapa);

		editora.setCidade(this.cidadeEditora);
		editora.setNome(this.nomeEditora);
		categoria.setNome(this.nomeCategoria);
		livro.setEditora(editora);

		livro.setEdicao(this.edicao);
		livro.setAno(this.ano);
		livro.setAutores(this.autoresCadastrados);
		livro.setCategoria(categoria);

		return livro;
	}
}
