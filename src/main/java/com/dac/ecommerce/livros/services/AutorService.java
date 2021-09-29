package com.dac.ecommerce.livros.services;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.Autor;
import com.dac.ecommerce.livros.repository.AutorRepository;

@Service
public class AutorService {

	@Autowired
	private AutorRepository repository;
	
<<<<<<< HEAD
	public List<Autor> todosAutores(){
		return repository.findAll();
=======
	public List<Autor> todosAutores() throws Exception{
		List<Autor> autores = repository.findAll();
		if(autores.size() == 0 || autores == null) {
			throw new Exception("Não existe autores cadastrados");
		}else {
			return repository.findAll();
		}
>>>>>>> branch 'main' of https://github.com/joaopaulopbjp/ifpb_monteiro_dac_2021_2_grupo_2.git
	}
	
	public void salvar(Autor autor) {
		repository.save(autor);
	}
	
	public Autor recuperarAutor(Long id) {
		return repository.getById(id);
	}
	
	public void remove(long ID) {
		repository.deleteById(ID);
	}
	
	public Autor pesquisarAutorPorNome(String nome) {
		Autor autor = repository.findUniqueByNome(nome);
		return autor;
	}
	
	public void editarAutor(Autor novoAutor, long idAntigo) {
		Autor autorSalvo = repository.findByID(idAntigo);
		BeanUtils.copyProperties(novoAutor, autorSalvo);
		repository.save(autorSalvo);
	}
	
	
	
	}

