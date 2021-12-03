package com.dac.ecommerce.livros.model.user;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;


//Classe que representa o usuário no Banco de Dados

@Entity
@Data
@Table(name="TB_USUARIO")
public class Usuario implements UserDetails{
	
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
	
	//@Enumerated(EnumType.STRING)
	//private TipoUsuario tipoUsuario;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles;

	// Construtores
		public Usuario() { }
		
	public String toString() {
		return "\nID: " + this.id +
				"\nE-mail: " + this.email +
				"\nNome: " + this.nome +
				"\nCPF: " + this.cpf + 
				"\nTelefone: " + this.telefone + 
				"\nTipo de Usuário: " + this.roles;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	
	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
