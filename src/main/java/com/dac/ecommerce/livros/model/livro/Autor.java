package com.dac.ecommerce.livros.model.livro;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

//Classe que representa um Autor no Banco de dados
@Data
@Entity
@Table(name = "TB_AUTOR")
public class Autor {
	
	@Id
	@Column(name = "autor_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	public Autor() {}
	
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "autores", fetch = FetchType.EAGER)
	private List<Livro> livros;
	
	public String toString() {
		return "\nID: " + this.id + "\nNome: " + this.nome;
	}


}
