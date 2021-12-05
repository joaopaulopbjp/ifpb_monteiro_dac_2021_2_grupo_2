package com.dac.ecommerce.livros.model.livro;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_LIVRO")
public class Livro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "livro_id")
	private Long id;
	
	private String isbn;
	
	private String titulo;
	
	private String descricao;
	
	private BigDecimal preco;
	
	@Lob
	private String imagemCapa;
	
	private Integer edicao;
	
	private boolean adicionadoEmEstoque;
	
	private Integer ano;
	
	@ManyToOne
	@JoinColumn(name = "editora_id")
	private Editora editora;
	
	@ManyToMany
	@JoinTable(name = "TB_LIVRO_AUTOR", 
	joinColumns = @JoinColumn(name ="livro_id"),
	inverseJoinColumns = @JoinColumn(name ="autor_id"))
	private List<Autor> autores;
	
	@ManyToOne
	private Categoria categoria;
	
	public Livro(){}
	
	@Override
	public String toString() {
		return "\nID: " + this.id + 
			   "\nTítulo: " + this.titulo + 
			   "\nDescrição: " + this.descricao + 
			   "\nEdição: " + this.edicao +
			   "\nAno: " + this.ano +
			   "\nPreço: " + this.preco +
			   "\nISBN: " + this.isbn;
	}
}
