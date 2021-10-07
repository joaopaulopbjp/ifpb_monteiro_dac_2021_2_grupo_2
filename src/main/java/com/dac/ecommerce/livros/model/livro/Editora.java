package com.dac.ecommerce.livros.model.livro;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	private String nome;
	
	public Editora () {}

	@Override
	public String toString() {
		return "Editora [id=" +
				id + ", cidade=" + cidade +
				", nome=" + nome + "]";
	}
	
	

}
