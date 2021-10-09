package com.dac.ecommerce.livros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.repository.EditoraRepository;

@Service
public class EditoraService {
	
	@Autowired
	private EditoraRepository editoraRepository;
	
	public Editora recuperarEditora(String nomeDaEditora, String cidadeEditora) {
		
		Editora editora = editoraRepository.findByNome(nomeDaEditora);
		
		if(editora == null) {
			editora = new Editora();
			editora.setNome(nomeDaEditora);
			editora.setCidade(cidadeEditora);
			editoraRepository.save(editora);
		}
		
		return editora;
		
	}
	
}
