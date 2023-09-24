package com.example.cadastro.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes") 

/* 
 * RequestMapping mostra por qual endpoint esse controller é responsável.
 * RestController define um controller
  */ 
 
public class ClientesController {

	@GetMapping 
	
	/* GetMapping Indica que esse método abaixo é responsável por cuidar das requisições
que baterem no /Clientes e sejam do método get
	*/ 
	
	public ResponseEntity GetAllClientes() {
		return ResponseEntity.ok("Deu ok!");
		/* ResponseEntity é o tipo de classe utilizada para a manipulação
		 * de dados retornados
		 */
	}
}
