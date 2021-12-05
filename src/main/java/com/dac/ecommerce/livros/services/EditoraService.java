package com.dac.ecommerce.livros.services;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.livro.Editora;
import com.dac.ecommerce.livros.repository.EditoraRepository;

@Service
public class EditoraService {
	
	@Autowired
	private EditoraRepository editoraRepository;
	
	public Editora recuperarEditora(String nomeDaEditora) {
		Editora editora = editoraRepository.findByNome(nomeDaEditora);
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
	
	public void alterarEditora(Editora novaEditora, Long id) {
		Editora editora = editoraRepository.findById(id).get();
		BeanUtils.copyProperties(novaEditora, editora);
		editoraRepository.save(editora);
	}
	
	public void excluirEditora(Long id) {
		editoraRepository.deleteById(id);
	}
}
