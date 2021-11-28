package com.dac.ecommerce.livros.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
	
	@RequestMapping("/menu-categoria")
	public String menu() {
		return "/categoria/menu-categoria";
	}

}
