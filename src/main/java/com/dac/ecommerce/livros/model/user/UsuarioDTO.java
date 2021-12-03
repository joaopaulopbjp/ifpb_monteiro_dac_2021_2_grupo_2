package com.dac.ecommerce.livros.model.user;

public class UsuarioDTO {
	
	private String nome;

	private String email;

	private String senha;

	private String CPF;
	
	public Usuario parser(){
		
		Usuario usuario = new Usuario();
		
		usuario.setNome(this.nome);
		
		usuario.setEmail(this.email);
		
		usuario.setCpf(this.CPF);
		
		usuario.setSenha(this.senha);
		
		return usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}
}
