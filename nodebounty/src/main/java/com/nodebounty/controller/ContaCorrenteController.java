package com.nodebounty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodebounty.config.errors.ErroCustomizadoDTO;
import com.nodebounty.config.errors.RegistroNaoEncontradoException;
import com.nodebounty.domain.contacorrente.DadosPlanoSelecionado;
import com.nodebounty.service.IContaCorrenteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/conta")
@SuppressWarnings("rawtypes")
public class ContaCorrenteController {

	@Autowired
	private IContaCorrenteService service;
	
	// Método para cadastrar uma nova conta. É executado quando o cliente escolhe o plano
	@PostMapping
	@Transactional
	public ResponseEntity registerAccount(@RequestBody @Valid DadosPlanoSelecionado json, HttpServletRequest request) {
		// No token jwt eu tenho o id do cliente, basicamente aqui to puxando de lá o id
		var idCliente = request.getAttribute("idCliente");

		try {
			service.cadastrarContaCorrente(json, (String) idCliente);
			return ResponseEntity.ok().build();
		}
		catch(RegistroNaoEncontradoException e) {
			// Tratando exceções quando não encontra o registro no banco, para retornar um código HTTP 404 - Not found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroCustomizadoDTO(e.getMessage()));
		}
		catch (Exception e) {
			// Tratando qualquer outro tipo de erro, retornando um 400 - Bad request
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ErroCustomizadoDTO(e.getMessage()));
		}
	}
	
	// ESSE MÉTODO VAI RETORNAR OS DADOS DA CONTA DO CLIENTE COM BASE NO SEU ID. O ID É PUXADO DO TOKEN JWT NA AUTENTICAÇÃO AUTOMATICAMENTE
	@GetMapping
	public ResponseEntity getAccountInfo(HttpServletRequest request) {
		var idCliente = request.getAttribute("idCliente");
		
		try {
			var conta = service.listarDadosDaContaPeloIdDoCliente((String) idCliente);
			return ResponseEntity.ok(conta);
		}
		catch(RegistroNaoEncontradoException e) {
			// Tratando exceções quando não encontra o registro no banco, para retornar um código HTTP 404 - Not found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroCustomizadoDTO(e.getMessage()));
		}
		catch (Exception e) {
			// Tratando qualquer outro tipo de erro, retornando um 400 - Bad request
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ErroCustomizadoDTO(e.getMessage()));
		}
	}
	
}
