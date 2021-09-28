package com.dac.ecommerce.livros.exceptions;

public class PaginaInvalidaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaginaInvalidaException() {
		super("[ERROR] - O NÚMERO DA PÁGINA INFORMADA NÃO EXISTE");
	}
}
