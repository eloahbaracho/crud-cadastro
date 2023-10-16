package com.nodebounty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodebounty.domain.cliente.ClienteRepository;
import com.nodebounty.domain.contacorrente.ContaCorrente;
import com.nodebounty.domain.contacorrente.ContaCorrenteRepository;
import com.nodebounty.domain.plano.DadosPlanoSelecionado;
import com.nodebounty.domain.plano.PlanoRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/planos")
@SuppressWarnings("rawtypes")
public class PlanoController {
	
	/* A classe de plano não vai ser muito manipulada né. Só tem esse método de listagem mesmo, então acho que
	 * Não tem necessidade de criar uma classe de serviço. Melhor usar o repository direto igual na classe Cliente
	 */
	
	@Autowired
	private PlanoRepository planoRepository;
	
	@Autowired
	private ContaCorrenteRepository contaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	/* Rota de GET para listar todos os planos do banco. */
	/* Estamos retornando uma resposta 200 (ok) com o body contendo a listagem de todos os planos */
	@GetMapping
	public ResponseEntity<Object> consultaTodos(){
		var planos = planoRepository.findAll();
		return ResponseEntity.ok(planos);
	}
	
	/* Rota POST para cliente selecionar o plano desejado */
	@PostMapping
	public ResponseEntity selectPlano(@RequestBody DadosPlanoSelecionado json, HttpServletRequest request) {
		// No token jwt eu tenho o id do cliente, basicamente aqui to puxando de lá o id
		var idCliente = request.getAttribute("idCliente");
		var plano = planoRepository.findById(json.nomePlano());
		var cliente = clienteRepository.findById((String) idCliente);
		
		if (plano.isPresent() && cliente.isPresent()) {
			var conta = new ContaCorrente();
			conta.setPlano(plano.get());
			conta.setCliente(cliente.get());
			contaRepository.save(conta);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().body("Plano Inválido");
		}
	}
}
