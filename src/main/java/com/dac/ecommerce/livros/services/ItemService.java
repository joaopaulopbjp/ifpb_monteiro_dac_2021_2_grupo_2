package com.dac.ecommerce.livros.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dac.ecommerce.livros.model.ItemEstoque;
import com.dac.ecommerce.livros.repository.ItemEstoqueRepository;

@Service
public class ItemService {

	@Autowired
	private ItemEstoqueRepository itemEstoqueRepository;
	
	public List<ItemEstoque> bucarTodosOsItensDoEstoque(){
		List<ItemEstoque> itens = itemEstoqueRepository.findAll();
		if(itens.size() == 0) {
			return null;
		}
		return itens;
	}
}
