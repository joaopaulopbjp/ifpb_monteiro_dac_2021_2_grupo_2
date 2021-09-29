package com.dac.ecommerce.livros.exceptions;

public class PaginaInvalidaException extends Exception {
	public PaginaInvalidaException() {
		super("[ERROR] - O NÚMERO DA PÁGINA INFORMADA NÃO EXISTE");
	}
}
