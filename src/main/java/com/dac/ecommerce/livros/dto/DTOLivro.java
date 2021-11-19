package com.dac.ecommerce.livros.dto;
import java.math.BigDecimal;
import java.util.ArrayList;
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
	private BigDecimal preco;
	private String imagemCapa;
	private Editora editora = new Editora();
	private String nomeEditora;
	private String cidadeEditora;
	private String nomeCategoria;
	private Integer edicao;
	private Integer ano;
	private List<Autor> autoresCadastrados = new ArrayList<Autor>();
	private Categoria categoria = new Categoria();

	public Livro toLivro() {
		Livro livro = new Livro();
		livro.setIsbn(this.isbn);
		livro.setTitulo(this.titulo);
		livro.setDescricao(this.descricao);
		livro.setPreco(this.preco);
		//livro.setImagemCapa(this.imagemCapa);
		editora.setCidade(this.cidadeEditora);
		editora.setNome(this.nomeEditora);
		categoria.setNome(this.nomeCategoria);
		livro.setEditora(this.editora);
		livro.setEdicao(this.edicao);
		livro.setAno(this.ano);
		livro.setAutores(this.autoresCadastrados);
		livro.setCategoria(this.categoria);
		return livro;
	}

	@Override
	public String toString() {
		return "DTOLivro [isbn=" + isbn + ", titulo=" + titulo + ", descricao=" + descricao + ", preco=" + preco
				+ ", imagemCapa=" + imagemCapa+ ", editora=" + editora + ", edicao=" + edicao
				+ ", ano=" + ano + ", autores=" + autoresCadastrados + ", categoria=" + categoria + "]";
	}
}
