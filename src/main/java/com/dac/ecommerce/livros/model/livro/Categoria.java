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
@Table(name="TB_CATEGORIA")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoria_id")
	private Long id;
	
	@Column(unique = true)
	private String nome;
	
	@OneToMany(cascade = CascadeType.MERGE)
	private List<Livro> livros = new ArrayList<>();
	
	public Categoria() {}
	
	@Override
	public String toString() {
		return "\nID: " + this.id + "\nNome: " + this.nome;
	}
}
