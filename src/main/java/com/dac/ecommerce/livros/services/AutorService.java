package com.dac.ecommerce.livros.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dac.ecommerce.livros.model.livro.Autor;
import com.dac.ecommerce.livros.repository.AutorRepository;

@Service
public class AutorService {

	@Autowired
	private AutorRepository repository;

	//Retorna os autores cadastrados
	public List<Autor> todosAutores(){
		List<Autor> autores = repository.findAll();
		return autores;
	}
	
	//Salva um autor no Banco de Dados
	public void salvar(Autor autor) {
		repository.save(autor);
	}
	
	//Recuperar Autor
	public Autor recuperarAutor(Long id) throws Exception{
		Optional<Autor> autor = repository.findById(id);
		if(autor.get() == null) {
			throw new Exception("[ERROR] AUTOR NÃO ENCONTRADO!");
		}
		return autor.get();
	}
	
	//Remove autor do Banco de Dados
	public void remove(Long id) {
		repository.deleteById(id);
	}
	
	//Retorna lista de autores
	public List<Autor> retornarListaDeAutores() {
		List<Autor> listaDeAutores = repository.findAll();
		return listaDeAutores;
	}
	
	//Pesquisa autor pelo nome
	public Autor pesquisarAutorPorNome(String nome) {
		Autor autor = repository.findUniqueByNome(nome);
		return autor;
	}	
	
	//Pesquisa autor pelo ID
	public Autor findByID(Long ID) {
		Autor autor = repository.findById(ID).get();

		return autor;
	}
	
	//Atualiza um autor no banco
	public void editarAutor(Autor novoAutor, long idAntigo) {
		Autor autorSalvo = repository.findById(idAntigo).get();
		BeanUtils.copyProperties(novoAutor, autorSalvo);
		repository.save(autorSalvo);
	}

}

