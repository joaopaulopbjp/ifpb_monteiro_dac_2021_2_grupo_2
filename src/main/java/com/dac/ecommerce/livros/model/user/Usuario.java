package com.dac.ecommerce.livros.model.user;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Entity
@Data
@Table(name="TB_USUARIO")
public class Usuario implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Email
	@Column(unique = true)
	private String email;
	
	@Length(min=8, message = "{senhaCurta}")
	private String senha;
	
	@NotEmpty
	@Length(min=3)
	private String nome;
	
	@CPF(message = "{cpfInvalido}")
	@Column(unique=true, nullable = false)
	private String cpf;
	
	private String telefone;

	@Embedded
	private Endereco endereco;
	
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

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
	
	public String toString() {
		return "\nID: " + this.id +
				"\nE-mail: " + this.email +
				"\nNome: " + this.nome +
				"\nCPF: " + this.cpf + 
				"\nTelefone: " + this.telefone + 
				"\nTipo de Usuário: " + this.roles;
	}

	
}
