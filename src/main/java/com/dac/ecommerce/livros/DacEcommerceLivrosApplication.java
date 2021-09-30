package com.dac.ecommerce.livros;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.dac.ecommerce.livros.exceptions.PaginaInvalidaException;
import com.dac.ecommerce.livros.model.Autor;
import com.dac.ecommerce.livros.model.CategoriasLivros;
import com.dac.ecommerce.livros.model.Livro;
import com.dac.ecommerce.livros.services.AutorService;
import com.dac.ecommerce.livros.services.LivroService;

@SpringBootApplication
public class DacEcommerceLivrosApplication implements CommandLineRunner {
	
	private LivroService servicoLivro;
	private AutorService autorService;
	
	public DacEcommerceLivrosApplication(LivroService servicoLivro, AutorService autorService) {
		this.servicoLivro = servicoLivro;
		this.autorService = autorService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DacEcommerceLivrosApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		menu();
	}		

	public void menu() {
		
		Scanner input = new Scanner(System.in);
		
		String mensagemInputInvalido = "[ERROR] - OPÇÃO INVÁLIDA!";
		
		boolean flag = true;
		while(flag) {
			System.out.println("\n-- MENU PRINCIPAL --");
			System.out.print("[1] - Usuário \n[2] - Autor \n[3] - Livro \n[0] - Finalizar\nOpção: ");
			Integer opcaoMenuPrincipal = Integer.parseInt(input.nextLine());
			
			switch(opcaoMenuPrincipal) {
			case 0:
				flag = false;
				System.out.println("Volte Sempre!! :)");
				break;
			case 1:
				while(true) {
					System.out.println("\n-- MENU USUÁRIO --");
					System.out.print("[1] - Registrar Novo Usuário \n[2] - Consultar Usuário por E-mail \n[0] - Voltar \nOpção: ");
					Integer opcaoMenuUsuario = Integer.parseInt(input.nextLine());
					
					if(opcaoMenuUsuario == 0) {
						break;
					} else if(opcaoMenuUsuario == 1) {
						
					} else if(opcaoMenuUsuario == 2) {
					
					} else {
						System.out.println(mensagemInputInvalido);
					}
				} // loop-while
				break;
			case 2:
				while(true) {
					System.out.println("\n-- MENU AUTOR --");
					System.out.print("[1] - Registrar Novo Autor \n[2] - Alterar Autor \n[0] - Voltar \nOpção: ");
					Integer opcaoMenuAutor = Integer.parseInt(input.nextLine());
					
					if(opcaoMenuAutor == 0) {
						break;
					} else if(opcaoMenuAutor == 1) {
						
					} else if(opcaoMenuAutor == 2) {
						
					} else {
						System.out.println(mensagemInputInvalido);
					}
				} // loop-while
				break;
			case 3:
				while(true) {
					System.out.println("\n-- MENU LIVRO --");
					System.out.print(
							"[1] - Cadastrar Livro \n[2] - Alterar Livro " + 
							"\n[3] - Excluir Livro \n[4] - Cadastar ao Estoque " +
							"\n[5] - Consultar os 5 Livros Mais Baratos Disponíveis em Estoque" +
							"\n[6] - Consultar Todos os Livros \n[0] - Voltar \nOpção: "
							);
					Integer opcaoMenuLivro = Integer.parseInt(input.nextLine());
					
					if(opcaoMenuLivro == 0) {
						break;
					} else if(opcaoMenuLivro == 1) {
						try {
							List<Autor> autores = autorService.todosAutores();
							System.out.println("Autores cadastrados: ");
							for (int i = 0; i < autores.size(); i++) {
								System.out.println("Nome do autor: "+autores.get(i).getNome()+" / "+"id: "+autores.get(i).getId());
							}
							
							System.out.print("Qual a quantidade de autores do livro?: ");
							int qtdAutores = Integer.parseInt(input.nextLine());
							
							if(qtdAutores <= autores.size() && qtdAutores > 0) {
								List<Autor> addAutores = new ArrayList<>();
								Livro novoLivro = new Livro();
								for (int i = 0; i < qtdAutores; i++) {
									System.out.print("Digite o ID do Autor: ");
									Autor recuperarAutor = autorService.recuperarAutor(Long.parseLong(input.nextLine()));
									addAutores.add(recuperarAutor);
								}
								novoLivro.setAutores(addAutores);
								System.out.print("Digite ISBN do Livro: ");
								novoLivro.setIsbn(input.nextLine());
								System.out.print("Digite o Título do Livro: ");
								novoLivro.setTitulo(input.nextLine());
								System.out.print("Digite a Decrição do Livro: ");
								novoLivro.setDescricao(input.nextLine());
								System.out.print("Digite o preço do Livro: ");
								novoLivro.setPreco(new BigDecimal(Float.parseFloat(input.nextLine())));
								
								//System.out.print("ImagemCapa: ");
								//novoLivro.set(input.nextLine());
								while(true) {
									System.out.println("Escolha uma categoria");
									System.out.println("[1] - Informática");
									System.out.println("[2] - Romance");
									System.out.println("[3] - Aventura");
									System.out.println("[4] - Engenharia");
									String op = input.nextLine();
									if(op.equals("1")) {
										novoLivro.setCategoria(CategoriasLivros.INFORMATICA);
										break;
									}else if(op.equals("2")) {
										novoLivro.setCategoria(CategoriasLivros.ROMANCE);
										break;
									}else if(op.equals("3")) {
										novoLivro.setCategoria(CategoriasLivros.AVENTURA);
										break;
									}else if(op.equals("4")) {
										novoLivro.setCategoria(CategoriasLivros.ENGENHARIA);
										break;
									}
									
								}
								
								System.out.print("Edição do Livro: ");
								novoLivro.setEdicao(Integer.parseInt(input.nextLine()));
								System.out.print("Ano do Livro: ");
								novoLivro.setAno(Integer.parseInt(input.nextLine()));
								
								servicoLivro.salvar(novoLivro);
								
							}else {
								System.out.println(mensagemInputInvalido);
							}
							
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						
					} else if(opcaoMenuLivro == 2) {
						
					} else if(opcaoMenuLivro == 3) {
						
					} else if(opcaoMenuLivro == 4) {
						
					} else if(opcaoMenuLivro == 5) {
						
					} else if(opcaoMenuLivro == 6) {
						System.out.print("Informe a página que deseja consultar: ");
						Integer numeroPagina = Integer.parseInt(input.nextLine());
						
						try {
							String livrosConsulta = servicoLivro.getAllLivrosPorPagina(numeroPagina);
							System.out.println(livrosConsulta);
						} catch(PaginaInvalidaException erro) {
							System.out.println(erro.getMessage());
						}
					} else {
						System.out.println(mensagemInputInvalido);
					}
				} // loop-while
				break;
				
			}
		}
		
		input.close();
		
		
		
	}
}
