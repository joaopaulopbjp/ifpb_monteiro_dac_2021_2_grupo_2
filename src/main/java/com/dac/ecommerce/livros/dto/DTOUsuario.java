package com.dac.ecommerce.livros.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dac.ecommerce.livros.model.user.TipoUsuario;
import com.dac.ecommerce.livros.model.user.Usuario;

import lombok.Data;

@Data
public class DTOUsuario {
	
	@NotEmpty
	@CPF
	private String cpf;
	
	@NotEmpty
	@Length(min=3, max=200)
	private String nome;
	
	@Length(min=15, max=15)
	private String telefone;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	@Length(min=8)
	private String senha;
	
	private TipoUsuario tipoUsuario;
	
	public Usuario parser(){
		
		Usuario usuario = new Usuario();
		usuario.setNome(this.nome);
		usuario.setCpf(this.cpf);
		usuario.setTelefone(telefone);
		usuario.setEmail(this.email);
		usuario.setSenha(new BCryptPasswordEncoder().encode(this.senha));
		usuario.setTipoUsuario(this.tipoUsuario);
		
		return usuario;
		
	}
}
