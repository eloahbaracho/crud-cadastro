package com.nodebounty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodebounty.config.errors.RegistroNaoEncontradoException;
import com.nodebounty.domain.cliente.ClienteRepository;
import com.nodebounty.domain.contacorrente.ContaCorrente;
import com.nodebounty.domain.contacorrente.ContaCorrenteRepository;
import com.nodebounty.domain.contacorrente.DadosPlanoSelecionado;
import com.nodebounty.domain.plano.PlanoRepository;


@Service
public class ContaCorrenteService implements IContaCorrenteService {

	@Autowired
	private ContaCorrenteRepository contaRepository;
	@Autowired
	private PlanoRepository planoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	// Método cadastrar nova conta corrente
	public void cadastrarContaCorrente(DadosPlanoSelecionado data, String idCliente) throws Exception {
		var plano = planoRepository.findById(data.nomePlano());
		var cliente = clienteRepository.findById(idCliente);
		
		// Verificando se o cliente existe
		if (!cliente.isPresent()) {
			throw new RegistroNaoEncontradoException("Cliente não encontrado no sistema");
		}
		
		// Verificando se o plano existe
		if (!plano.isPresent()) {
			throw new RegistroNaoEncontradoException("Plano não encontrado no sistema");
		}
		
		// Verificando se o cliente já possui uma conta corrente associada
		if (contaRepository.existsByCliente(cliente.get())) {
			throw new Exception("Esse cliente já possui uma conta corrente associada a ele");
		}
		
		// Fluxo bem sucedido, criando a conta corrente
		var conta = new ContaCorrente();
		conta.setCliente(cliente.get());
		conta.setPlano(plano.get());
		
		contaRepository.save(conta);
	}

	// Método recuperar dados de uma conta corrente de um cliente em específico pelo id
	public ContaCorrente listarDadosDaContaPeloIdDoCliente(String idCliente) throws Exception {
		var cliente = clienteRepository.findById(idCliente);
		// Verificando se o cliente existe
		if (!cliente.isPresent()) {
			throw new RegistroNaoEncontradoException("Cliente não encontrado no sistema");
		}
		
		var conta = contaRepository.findByCliente(cliente.get());
		// Verificando se a conta existe
		if (conta == null) {
			throw new RegistroNaoEncontradoException("Cliente não possui nenhuma conta associada no sistema");
		}
		
		// Fluxo bem sucedido, retornando dados da conta corrente
		return conta;
	}

}
