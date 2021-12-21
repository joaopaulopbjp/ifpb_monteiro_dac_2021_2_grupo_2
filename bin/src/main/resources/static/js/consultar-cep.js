function pesquisar(cep) {
	
	cep = cep.replace('-', '');
	
	if(cep != '') {
		
		document.getElementById('input-rua').value="...";
		document.getElementById('input-cidade').value="...";
		document.getElementById('input-bairro').value="...";
		document.getElementById('input-estado').value="...";
		
		const script = document.createElement('script');
		script.src = 'https://viacep.com.br/ws/'+ cep + '/json/?callback=atualizarDados';
		
		document.body.appendChild(script);
		
	} else {
		clear();
	}
}

function atualizarDados(dados) {
	if(!('erro' in dados)) {
		document.getElementById('input-rua').value=(dados.logradouro);
		document.getElementById('input-cidade').value=(dados.localidade);
		document.getElementById('input-bairro').value=(dados.bairro);
		document.getElementById('input-estado').value=(dados.uf);
	} else {
		alert('CEP não encontrado!');
	}
}

function clear() {
	document.getElementById('input-rua').value="";
	document.getElementById('input-cidade').value="";
	document.getElementById('input-bairro').value="";
	document.getElementById('input-estado').value="";
}
