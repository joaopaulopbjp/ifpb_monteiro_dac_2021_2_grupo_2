package com.dac.ecommerce.livros.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/livro")
public class LivroController {

	@RequestMapping("/pagina")
	public String pagina(){
		return "pageLivro";
	}
}
