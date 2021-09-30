package com.dac.ecommerce.livros.model;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TB_LIVRO")
public class Livro {
	
	@Id
	@Column(name = "livro_isbn")
	private String isbn;
	
	private String titulo;
	
	private String descricao;
	
	private BigDecimal preco;
	
	private byte[] imagemCapa;
	
	private Integer edicao;
	
	private Integer ano;
	
	@ManyToMany
	@JoinTable(name = "TB_LIVRO_AUTOR", 
	joinColumns = @JoinColumn(name ="livro_isbn"),
	inverseJoinColumns = @JoinColumn(name ="autor_id"))
	private List<Autor> autores;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoriasLivros categoria;
	
	public String toString() {
		return "\n\nTítulo: " + this.titulo + 
			   "\nDescrição: " + this.descricao + 
			   "\nEdição: " + this.edicao +
			   "\nAno: " + this.ano +
			   "\nPreço: " + this.preco +
			   "\nISBN: " + this.isbn;
	}
}
