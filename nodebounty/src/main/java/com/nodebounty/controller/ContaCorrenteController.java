package com.nodebounty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodebounty.domain.cliente.ClienteRepository;
import com.nodebounty.domain.contacorrente.ContaCorrenteRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/conta")
@SuppressWarnings("rawtypes")
public class ContaCorrenteController {

	@Autowired
	private ContaCorrenteRepository contaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	// ESSE MÉTODO VAI RETORNAR OS DADOS DA CONTA DO CLIENTE COM BASE NO SEU ID. O ID É PUXADO DO TOKEN JWT NA AUTENTICAÇÃO AUTOMATICAMENTE
	@GetMapping
	public ResponseEntity getAccountInfo(HttpServletRequest request) {
		var idCliente = request.getAttribute("idCliente");
		var cliente = clienteRepository.findById((String) idCliente);
		
		if (cliente.isPresent()) {
			var conta = contaRepository.findByCliente(cliente.get());
			return ResponseEntity.ok(conta);
		} else {
			return ResponseEntity.badRequest().body("Cliente não encontrado");
		}
	}
	
}
