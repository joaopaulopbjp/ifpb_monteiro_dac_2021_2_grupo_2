package com.dac.ecommerce.livros.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.model.livro.Livro;

import lombok.Data;

@Data // (Data Transfer Object)
public class DTOLivro {

	
	private String isbn;
	private Long idLivro;
	private String titulo;
	private String descricao;
	private String preco;
	private Long editora;
	private Long categoria;
	private Integer edicao;
	private String date;
	
	@NotEmpty
	@NotNull
	private List<Autor> autoresCadastrados = new ArrayList<>();
	
	public Livro toLivro(){
		Livro livro = new Livro();
		livro.setId(this.idLivro);
		livro.setIsbn(this.isbn);
		livro.setTitulo(this.titulo);
		livro.setDescricao(this.descricao);
		livro.setPreco(new BigDecimal(this.preco.replace(",", ".")));
		livro.setEdicao(this.edicao);
		livro.setAno(Integer.parseInt(this.date.substring(0, 4)));
		livro.setAutores(this.autoresCadastrados);
		livro.setAdicionadoEmEstoque(false);
		return livro;
	}
}
