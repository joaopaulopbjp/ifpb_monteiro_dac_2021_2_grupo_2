package com.dac.ecommerce.livros.dto;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;
import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.model.livro.Categoria;
import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.model.livro.Livro;

import lombok.Data;

@Data // (Data Transfer Object)
public class DTOLivro {

	@NotNull
	@NotBlank
	@ISBN
	private String isbn;
	
	private Long idLivro;
	
	@NotBlank
	@Size(min = 2, max = 50)
	private String titulo;
	
	@NotBlank
	@Size(min = 2, max = 150)
	private String descricao;
	
	@NotBlank
	@Size(min = 1, max = 6)
	private String preco;
	
	@NotNull
	private Editora editora;
	
	@NotNull
	private Categoria categoria;
	
	@Positive
	@NotNull
	private Integer edicao;
	
	private  File file;
	
	@NotBlank
	@NotNull
	private String date;
	
	@NotEmpty
	@NotNull
	@Size(min=1, max=5)
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
		livro.setCategoria(this.categoria);
		livro.setEditora(this.editora);
		livro.setAdicionadoEmEstoque(false);
		return livro;
	}
}
