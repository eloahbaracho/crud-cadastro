package com.nodebounty.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodebounty.domain.cliente.ClienteRepository;
import com.nodebounty.domain.contacorrente.ContaCorrenteRepository;
import com.nodebounty.domain.transacao.DadosDepositoTransacao;
import com.nodebounty.domain.transacao.DadosListagemTransacao;
import com.nodebounty.domain.transacao.DadosSaqueTransacao;
import com.nodebounty.domain.transacao.DadosTransferenciaTransacao;
import com.nodebounty.domain.transacao.Transacao;
import com.nodebounty.domain.transacao.TransacaoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transacoes")
@SuppressWarnings("rawtypes")
public class TransacaoController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ContaCorrenteRepository contaRepository;

	@Autowired
	private TransacaoRepository transacaoRepository;

	// Método para depositar valor na conta
	@PostMapping("/depositar")
	@Transactional
	public ResponseEntity depositar(@RequestBody @Valid DadosDepositoTransacao json, HttpServletRequest request) {
		var idCliente = request.getAttribute("idCliente");

		// Recuperando dados do cliente
		var cliente = clienteRepository.findById((String) idCliente);

		// Se o id do cliente no token não encontrou nenhum cliente no banco, retornar
		// erro 404
		if (!cliente.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado no sistema");
		}

		// Recuperando a conta
		var conta = contaRepository.findByCliente(cliente.get());

		// Se o id do cliente não tem nenhuma conta associada no sistema, retornar erro
		// 404
		if (conta == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada no sistema");
		}

		// Depositando o valor na conta do cliente que fez a requisição
		conta.depositar(json.valor());
		contaRepository.save(conta);

		// Salvando o registro da transação
		// Mudei no banco e permite que emissor e receptor fosse nulos
		// Como esse é um depósito, tem mais sentido que o emissor seja nulo (Já que não
		// existe)
		// E o receptor a conta do cliente. Assim no front vai ser possível listar
		// entradas / saídas de forma mais eficaz
		var transacao = new Transacao();
		transacao.setValorTransacao(json.valor());
		transacao.setReceptor(conta);
		transacaoRepository.save(transacao);

		// Retornando os dados da transação pro front-end, como 'comprovante'
		return ResponseEntity.ok(transacao);
	}

	@PostMapping("/sacar")
	@Transactional
	public ResponseEntity sacar(@RequestBody @Valid DadosSaqueTransacao json, HttpServletRequest request) {
		var idCliente = request.getAttribute("idCliente");
		var valor = json.valor();
		var cliente = clienteRepository.findById((String) idCliente);
		if (!cliente.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não pode ser encontrado no sistema");
		}
		var conta = contaRepository.findByCliente(cliente.get());
		if (conta == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada no sistema");
		}

		var saldoConta = conta.getSaldoConta();

		if (valor > saldoConta) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("O valor precisa ser menor ou igual ao saldo da conta");
		}

		conta.sacar(json.valor());
		contaRepository.save(conta);
		var transacao = new Transacao();
		transacao.setValorTransacao(json.valor());
		transacao.setEmissor(conta);
		transacaoRepository.save(transacao);
		return ResponseEntity.ok(transacao);
	}

	@PostMapping("/transferir")
	@Transactional
	public ResponseEntity transferir(@RequestBody @Valid DadosTransferenciaTransacao json, HttpServletRequest request) {
		var idCliente = request.getAttribute("idCliente");
		var valor = json.valor();
		var clienteEmissor = clienteRepository.findById((String) idCliente);
		if (!clienteEmissor.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
		}

		var contaEmissor = contaRepository.findByCliente(clienteEmissor.get());
		if (contaEmissor == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A conta emissora não existe");
		}

		var contaReceptor = contaRepository.findByNumeroConta(json.numeroConta());
		if (contaReceptor == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A conta designada não foi encontrada");
		}

		if (valor > contaEmissor.getSaldoConta()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("O valor precisa ser menor ou igual ao saldo da conta");
		}

		if (contaReceptor.getCliente().getEmail().equals("mac@gmail.com")
				&& contaEmissor.getPlano().getIdPlano().equals("Beauty")) {
			var valorCashback = valor * (contaEmissor.getPlano().getPorcentagemCashback() / 100);
			contaEmissor.cashback(valorCashback);
		}
		if (contaReceptor.getCliente().getEmail().equals("kabum@gmail.com")
				&& contaEmissor.getPlano().getIdPlano().equals("Tech")) {
			var valorCashback = valor * (contaEmissor.getPlano().getPorcentagemCashback() / 100);
			contaEmissor.cashback(valorCashback);
		}
		if (contaReceptor.getCliente().getEmail().equals("growth@gmail.com")
				&& contaEmissor.getPlano().getIdPlano().equals("Health")) {
			var valorCashback = valor * (contaEmissor.getPlano().getPorcentagemCashback() / 100);
			contaEmissor.cashback(valorCashback);
		}

		contaEmissor.sacar(valor);
		contaReceptor.depositar(valor);
		contaRepository.save(contaEmissor);
		contaRepository.save(contaReceptor);

		var transacao = new Transacao();
		transacao.setEmissor(contaEmissor);
		transacao.setReceptor(contaReceptor);
		transacao.setValorTransacao(valor);
		transacaoRepository.save(transacao);

		return ResponseEntity.ok(transacao);
	}

	@GetMapping
	public ResponseEntity listarTransacoesPorIdCliente(HttpServletRequest request) {
		var idCliente = request.getAttribute("idCliente");

		// Recuperando dados do cliente
		var cliente = clienteRepository.findById((String) idCliente);

		// Se o id do cliente no token não encontrou nenhum cliente no banco, retornar
		// erro 404
		if (!cliente.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado no sistema");
		}

		// Recuperando a conta
		var conta = contaRepository.findByCliente(cliente.get());

		// Se o id do cliente não tem nenhuma conta associada no sistema, retornar erro
		// 404
		if (conta == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada no sistema");
		}
		
		// Recuperando e listando todas transações que a conta do cliente está envolvida
		var transacoesComoEmissor = transacaoRepository.findAllByEmissor(conta);
		var transacoesComoReceptor = transacaoRepository.findAllByReceptor(conta);
		
		// Aqui vou salvar uma array de transações e a informação do tipo dela. Será algo como
		// [{ transacao: {...dados da transacao}, role: 'entrada'}, transacao: {...dados da transacao}, role: 'saida'}]
		List<DadosListagemTransacao> transacoes = new ArrayList<>();
		
		// Salvando as transações como emissor/saidas na nossa array transacoes
		transacoesComoEmissor.forEach(emissor -> transacoes.add(new DadosListagemTransacao(emissor, "saida")));
		
		// Salvando as transações como receptor/entradas na nossa array transacoes
		transacoesComoReceptor.forEach(receptor -> transacoes.add(new DadosListagemTransacao(receptor, "entrada")));
		
		return ResponseEntity.ok(transacoes);
	}

	@PostMapping("/resgatar")
	public ResponseEntity resgatar(HttpServletRequest request) {
		var idCliente = request.getAttribute("idCliente");

		var cliente = clienteRepository.findById((String) idCliente);
		if (!cliente.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
		}

		var conta = contaRepository.findByCliente(cliente.get());
		if (conta == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A conta emissora não existe");
		}

		conta.resgatarCashback();
		contaRepository.save(conta);
		return ResponseEntity.ok().build();
	}

}
