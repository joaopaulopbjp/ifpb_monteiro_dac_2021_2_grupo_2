package com.dac.ecommerce.livros.exceptions;

public class LivroAutorException extends Exception {
	public LivroAutorException() {
		super("[ERROR] - LIVRO NÃO POSSUI AUTOR ATRELADO!");
	}
}
