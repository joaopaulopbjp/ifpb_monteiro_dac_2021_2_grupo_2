package com.dac.ecommerce.livros.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.model.livro.Livro;

import lombok.Data;

@Data // (Data Transfer Object)
public class DTOLivro {

	@NotEmpty
	private String isbn;
	@NotEmpty
	private String titulo;
	@NotEmpty
	private String descricao;
	@NotEmpty
	private String preco;
	@NotEmpty
	private String imagemCapa;
	@NotEmpty
	private String nomeEditora;
	@NotEmpty
	private String cidadeEditora;
	@NotEmpty
	private String nomeCategoria;
	@Positive
	private Integer edicao;
	@Positive
	private Integer ano;
	@NotEmpty
	private List<Autor> autoresCadastrados = new ArrayList<Autor>();

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
