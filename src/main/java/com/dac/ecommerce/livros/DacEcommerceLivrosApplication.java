package com.dac.ecommerce.livros;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dac.ecommerce.livros.exceptions.LivroException;
import com.dac.ecommerce.livros.exceptions.PaginaInvalidaException;
import com.dac.ecommerce.livros.model.*;
import com.dac.ecommerce.livros.model.pedido.PedidoFacade;
import com.dac.ecommerce.livros.services.*;

@SpringBootApplication
public class DacEcommerceLivrosApplication implements CommandLineRunner {
	
	private LivroService livroService;
	private AutorService autorService;
	private FormaPagamentoService formaPagamentoService;
	private EstoqueService estoqueService;
	private ItemService itemService;
	private PedidoService pedidoService;
	
	public DacEcommerceLivrosApplication(
		LivroService livroService, PedidoService pedidoService,
		AutorService autorService, FormaPagamentoService formaPagamentoService,
		EstoqueService estoqueService, ItemService itemService) {
			this.livroService = livroService;
			this.autorService = autorService;
			this.formaPagamentoService = formaPagamentoService;
			this.estoqueService = estoqueService;
			this.itemService = itemService;
			this.pedidoService = pedidoService;	
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
			System.out.print("[1] - Usuário \n[2] - Autor \n[3] - Livro \n[4] - Pedido \n[0] - Finalizar\nOpção: ");
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
							Autor autor1 = new Autor();
							autor1.setNome("fulano");
							autorService.salvar(autor1);
							List<Autor> autores = autorService.todosAutores();
							System.out.println("Autores cadastrados: ");
							for (int i = 0; i < autores.size(); i++) {
								System.out.println(autores.get(i).toString());
							}
							System.out.print("Qual a quantidade de autores do livro?: ");
							int qtdAutores = Integer.parseInt(input.nextLine());
							List<Autor> addAutores = null;
							if(qtdAutores <= autores.size() && qtdAutores > 0) {
								addAutores = new ArrayList<>();
								for (int i = 0; i < qtdAutores; i++) {
									System.out.print("Digite o ID do Autor: ");
									Autor recuperarAutor = autorService.recuperarAutor(Long.parseLong(input.nextLine()));
									addAutores.add(recuperarAutor);
								}
							}else {
								System.out.println(mensagemInputInvalido);
							}
							if(addAutores != null) {
								System.out.print("Digite ISBN do Livro: ");
								String isbn = input.nextLine();
								System.out.print("Digite o Título do Livro: ");
								String tituloLivro = input.nextLine();
								System.out.print("Digite a Decrição do Livro: ");
								String descricao = input.nextLine();
								System.out.print("Digite o preço do Livro: ");
								BigDecimal preco = new BigDecimal(Float.parseFloat
								(input.nextLine()));
//								//System.out.print("ImagemCapa: ");
//								//novoLivro.set(input.nextLine());
								System.out.print("Digite a categoria do Livro: ");
								String categoria = input.nextLine();
								System.out.print("Edição do Livro: ");
								Integer edicao = Integer.parseInt(input.nextLine());
								System.out.print("Ano do Livro: ");
								Integer ano = Integer.parseInt(input.nextLine());
								livroService.salvarLivro(addAutores, isbn, categoria,
								tituloLivro, descricao,preco, null, edicao, ano);
								System.out.println();
								System.out.println("Livro Cadastrado Com Sucesso!");
							}
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} else if(opcaoMenuLivro == 2) {
						System.out.print("Digite o ISBN do Livro: ");
						try {
							Livro livro1 = livroService.bucarLivroPeloIsbn(input.nextLine());
							System.out.print("Digite um novo valor para o Livro: ");
							livro1.setPreco(new BigDecimal(Float.parseFloat(input.nextLine())));
							livroService.alterarLivro(livro1);
							System.out.println("Livro Alterado com Sucesso!");
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} else if(opcaoMenuLivro == 3) {
						try {
							System.out.print("Digite o ISBN do Livro: ");
							System.out.println(livroService.excluirLivro(input.nextLine()));
						} catch (LivroException e) {
							System.out.println(e.getMessage());
						}
					} else if(opcaoMenuLivro == 4) {
						try {
							System.out.println();
							if(itemService.bucarTodosOsItensDoEstoque() != null) {
								System.out.println("***LIVROS CADASTRADOS NO ESTOQUE***");
								for(int i = 0;i < itemService.bucarTodosOsItensDoEstoque().size();i++) {
									System.out.println("ITEM : "+itemService
									.bucarTodosOsItensDoEstoque().get(i));
								}
							}
							System.out.println();
							System.out.print("Digite o ISBN do Livro: ");
							String isbn = input.nextLine();
							System.out.print("Digite a quantidade de livros: ");
							Integer qtd = Integer.parseInt(input.nextLine());
							System.out.println(estoqueService.adicionarNoEstoque(isbn, qtd));
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} else if(opcaoMenuLivro == 5) {
						try {
							System.out.println(estoqueService.consultarLivrosMaisBaratosDoEstoque());
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} else if(opcaoMenuLivro == 6) {
						System.out.print("Informe a página que deseja consultar: ");
						Integer numeroPagina = Integer.parseInt(input.nextLine());
						
						try {
							System.out.println(livroService.listarLivrosPorPaginacao(numeroPagina));
						} catch(PaginaInvalidaException erro) {
							System.out.println(erro.getMessage());
						}
					} else {
						System.out.println(mensagemInputInvalido);
					}
				} // loop-while
				break;
			case 4:
				while(true) {
					System.out.println("\n-- MENU PEDIDO --");
					System.out.print(
							"[1] - Novo Pedido \n[2] - Cadastrar Forma Pagamento " + 
							"\n[3] - Cancelar Pedido \n[0] - Voltar \nOpção: "
							);
					Integer opcaoMenuPedido = Integer.parseInt(input.nextLine());
					
					if(opcaoMenuPedido == 1) {
						
						try {
							PedidoFacade pedidoFacade = new PedidoFacade(pedidoService, livroService, formaPagamentoService);
							pedidoFacade.criarPedido();
							
							// Definir endereço de entrega
							System.out.println("\nInforme o endereço de entrega");
							System.out.print("Cidade: ");
							String cidade = input.nextLine();
							
							System.out.print("Rua: ");
							String rua = input.nextLine();
							
							System.out.print("Complemento: ");
							String complemento = input.nextLine();
							
							System.out.print("CEP: ");
							String cep = input.nextLine();
							
							System.out.print("Número: ");
							Integer numero = Integer.parseInt(input.nextLine());
							
							System.out.print("Bairro: ");
							String bairro = input.nextLine();
							
							System.out.print("Estado: ");
							String estado = input.nextLine();
							
							pedidoFacade.definirEnderecoEntrega(cep, numero, cidade, estado, bairro, rua, complemento);
							
							// Definir forma de pagamento
							System.out.println("\nInforme o método de pagamento");
							pedidoFacade.imprimirFormasPagamento();
							
							System.out.print("\nInforme o número da forma de pagamento: ");
							Long idFormaPagamento = Long.parseLong(input.nextLine());
							
							pedidoFacade.definirFormaPagamento(idFormaPagamento);
							pedidoFacade.registrarPedido();
							
							// Adicionar Items
							System.out.println("\nInforme o(s) livro(s) que deseja comprar");
							while(true) {
								pedidoFacade.imprimirLivros();
								
								System.out.print("\nNúmero do livro: ");
								Long idLivro = Long.parseLong(input.nextLine());
								
								System.out.print("Quantidade: ");
								Integer quantidade = Integer.parseInt(input.nextLine());
								
								pedidoFacade.adicionarLivro(idLivro, quantidade);
								
								// Verificar condição de parada
								System.out.print("\nDeseja adicionar outro livro? [S - Sim | N - não]: ");
								String flagParada = input.nextLine().toUpperCase();
								
								if(flagParada.equals("N")) {
									break;
								}
							}
							
							// Finalizar Pedido
							pedidoFacade.finalizarPedido();
							
						} catch(Exception erro) {
							System.out.println(erro.getMessage());
						}
						
					} else if(opcaoMenuPedido == 2) {
						System.out.print("\nInforme a forma de pagamento: ");
						String formaPagamento = input.nextLine();
						formaPagamentoService.salvar(formaPagamento);
					} else if(opcaoMenuPedido == 3) {
						try {
							System.out.print("\nInforme o ID do pedido: ");
							Long idPedido = Long.parseLong(input.nextLine());
							
							System.out.print("Informa o motivo do cancelamento: ");
							String motivo = input.nextLine();
							
							pedidoService.cancelarPedido(idPedido, motivo);
						} catch(Exception erro) {
							System.out.println(erro.getMessage());
						}
					} else if(opcaoMenuPedido == 0) {
						break;
					} else {
						System.out.println(mensagemInputInvalido);
					}
				} // loop-while
				break;
			default:
				System.out.println(mensagemInputInvalido);
			}
		}
		input.close();
		System.exit(0);
	}
}
