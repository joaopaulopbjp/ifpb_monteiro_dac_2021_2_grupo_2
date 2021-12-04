package com.dac.ecommerce.livros.model.livro;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="TB_EDITORA")
public class Editora {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cidade;
	
	@Column(unique = true)
	private String nome;
	
	@OneToMany(cascade = CascadeType.MERGE)
	private List<Livro> livros = new ArrayList<>();
	
	public Editora () {}
	
	@Override
	public String toString() {
		return "Editora [id=" +
				id + ", cidade=" + cidade +
				", nome=" + nome + "]";
	}
	
	

}
