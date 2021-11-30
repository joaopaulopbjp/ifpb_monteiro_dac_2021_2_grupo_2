package com.dac.ecommerce.livros.services;

import java.util.List;

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
	
	public List<Editora> todasEditoras(){
		List<Editora> editoras = editoraRepository.findAll();
		return editoras;
	}
	
	public void salvar(Editora editora) {
		editoraRepository.save(editora);
	}
	
	public Editora buscarEditora(Long id) {
		Editora editora = editoraRepository.findById(id).get();
		return editora;
	}
}
