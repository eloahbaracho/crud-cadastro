package com.example.cadastro.controllers;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cadastro.domain.Clientes;
import com.example.cadastro.domain.ClientesRepository;
import com.example.cadastro.domain.RequestClientes;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes") 

/* 
 * RequestMapping mostra por qual endpoint esse controller é responsável.
 * RestController define um controller
  */ 
 
public class ClientesController {
	@Autowired /* Injeção de dependência */ 
	private ClientesRepository repository;
	@GetMapping 
	
	/* GetMapping Indica que esse método abaixo é responsável por cuidar das requisições
que baterem no /Clientes e sejam do método get
	*/ 
	
	public ResponseEntity GetAllClientes() {
		var allClientes = repository.findAll();
		return ResponseEntity.ok(allClientes);
		/* ResponseEntity é o tipo de classe utilizada para a manipulação
		 * de dados retornados
		 */
		
	}
	@PostMapping
	public ResponseEntity registerClientes(@RequestBody @Valid RequestClientes data) {
		Clientes newClientes = new Clientes(data);
		repository.save(newClientes);
		System.out.print(data);
		return ResponseEntity.ok().build();
		/*  RequestBody indica que ele quer buscar os valores mostrados 
		 * através da requisição feita 
		  O valid vai fazer a validação do body que chegou via requisição pelo record
		  requestclientes 
		  Exemplo: precisa vir um nome que é uma string e um endereço que 
		  também é uma string.
		  */
		
		/* o método ok() sem parâmetros é retornado um objeto do tipo BodyBuilder, 
		 * mas o método ok(...) 
		 * com parâmetros já devolve direto um objeto ResponseEntity.
		 */
	}
	
	@PutMapping /*putmapping é o método para atualizar */
	@Transactional
	public ResponseEntity updateClientes(@RequestBody @Valid RequestClientes data) {
		Optional <Clientes> optionalClientes = repository.findById(data.id());
		if(optionalClientes.isPresent()) {
			Clientes clientes = optionalClientes.get();
			clientes.setNome(data.Nome());
			clientes.setEndereco(data.Endereco());
			clientes.setCep(data.Cep());
			clientes.setNumero(data.Numero());
			clientes.setRg(data.Rg());
			clientes.setCpf(data.Cpf());
			clientes.setEmail(data.Email());
			clientes.setDataNascimento(data.DataNascimento());
			return ResponseEntity.ok(clientes);
		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
}
