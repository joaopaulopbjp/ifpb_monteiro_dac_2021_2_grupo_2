package com.dac.ecommerce.livros.model;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_AUTOR")
public class Autor {
	
	@Id
	@Column(name = "autor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@ManyToMany(cascade = CascadeType.ALL,mappedBy = "autores")
	private List<Livro> livros;
	
	public Autor() { }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public Long getId() {
		return id;
	}
	
	public String toString() {
		return "\nID: " + this.id + "\nNome: " + this.nome;
	}


}
