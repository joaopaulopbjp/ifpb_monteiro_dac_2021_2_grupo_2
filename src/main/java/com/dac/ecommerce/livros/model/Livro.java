package com.dac.ecommerce.livros.model;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	
	private String descricao;
	
	private BigDecimal preco;
	
	private String isbn;
	
	private byte[] imagemCapa;
	
	private Integer edicao;
	
	private Integer ano;
	
	@OneToMany
	@JoinColumn(name = "autor_id")
	private List<Autor> autores;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoriasLivros categoria;
	
	public Livro() {
		// TODO Auto-generated constructor stub
	}

	public byte[] getImagemCapa() {
		return imagemCapa;
	}


	public void setImagemCapa(byte[] imagemCapa) {
		this.imagemCapa = imagemCapa;
	}


	public List<Autor> getAutores() {
		return autores;
	}


	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}


	public Long getId() {
		return id;
	}


	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Integer getEdicao() {
		return edicao;
	}

	public void setEdicao(Integer edicao) {
		this.edicao = edicao;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public CategoriasLivros getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriasLivros categoria) {
		this.categoria = categoria;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
}
