package com.dac.ecommerce.livros.model.user;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name="TB_USUARIO")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(unique=true, nullable = false)
	private String cpf;
	
	private String telefone;

	@Embedded
	private Endereco endereco;
	
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	public String toString() {
		return "\nID: " + this.id +
				"\nE-mail: " + this.email +
				"\nNome: " + this.nome +
				"\nCPF: " + this.cpf + 
				"\nTelefone: " + this.telefone + 
				"\nTipo de Usuário: " + this.tipoUsuario;
	}
	
}
