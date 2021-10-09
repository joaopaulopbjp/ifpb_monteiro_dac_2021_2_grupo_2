package com.dac.ecommerce.livros.model.livro;
import javax.persistence.*;
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
	
	public Editora () {}

	@Override
	public String toString() {
		return "Editora [id=" +
				id + ", cidade=" + cidade +
				", nome=" + nome + "]";
	}
	
	

}
