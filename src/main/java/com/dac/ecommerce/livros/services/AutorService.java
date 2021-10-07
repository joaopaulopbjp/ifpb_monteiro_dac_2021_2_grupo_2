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

	public List<Autor> todosAutores() throws Exception{
		List<Autor> autores = repository.findAll();
		if(autores.size() == 0) {
			throw new Exception("[ERROR] NÃO EXISTE AUTORES CADASTRADOS");
		}else {
			return repository.findAll();
		}
	}
	
	public void salvar(Autor autor) {
		repository.save(autor);
	}
	
	public Autor recuperarAutor(Long id) throws Exception{
		Optional<Autor> autor = repository.findById(id);
		if(autor.get() == null) {
			throw new Exception("[ERROR] AUTOR NÃO ENCONTRADO!");
		}
		return autor.get();
	}
	
	public void remove(Long id) {
		repository.deleteById(id);
	}
	
	public List<Autor> retornarListaDeAutores() {
		List<Autor> listaDeAutores = repository.findAll();
		return listaDeAutores;
	}
	
	public Autor pesquisarAutorPorNome(String nome) {
		Autor autor = repository.findUniqueByNome(nome);
		return autor;
	}	
	
	public Autor findByID(long ID) {
		Autor autor = repository.findById(ID);

		return autor;
	}
	
	public void editarAutor(Autor novoAutor, long idAntigo) {
		Autor autorSalvo = repository.findById(idAntigo);
		BeanUtils.copyProperties(novoAutor, autorSalvo);
		repository.save(autorSalvo);
	}

}

