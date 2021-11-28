package com.dac.ecommerce.livros.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editora")
public class EditoraController {
	
	
	@RequestMapping("/menu-editora")
	public String menu() {
		return "/editora/menu-editora";
	}

}
