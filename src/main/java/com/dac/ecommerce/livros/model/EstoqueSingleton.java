package com.dac.ecommerce.livros.model;

public class EstoqueSingleton {
	
	private static Estoque estoque;
	
	private EstoqueSingleton() {}
	
	public static synchronized Estoque getEstoque() {
		if(estoque == null) {
			estoque = new Estoque();
		}
		return estoque;
	}
}
