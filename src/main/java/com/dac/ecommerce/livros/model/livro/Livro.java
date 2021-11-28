package com.dac.ecommerce.livros.model.livro;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Editora editora;
	
	private Integer edicao;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ano;
	
	@ManyToMany
	@JoinTable(name = "TB_LIVRO_AUTOR", 
	joinColumns = @JoinColumn(name ="livro_id"),
	inverseJoinColumns = @JoinColumn(name ="autor_id"))
	private List<Autor> autores;
	
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
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
