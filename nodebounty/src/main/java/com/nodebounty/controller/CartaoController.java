package com.nodebounty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodebounty.config.errors.ErroCustomizadoDTO;
import com.nodebounty.config.errors.RegistroNaoEncontradoException;
import com.nodebounty.service.ICartaoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/cartoes")
@SuppressWarnings("rawtypes")
public class CartaoController {
	
	@Autowired
	private ICartaoService service;

	// Criando um novo cartao, pegando o id do cliente pelo token
	/* Usamos Transactional geralmente quando estamos MODIFICANDO dados no banco. 
	 * Se houver algum erro no meio de uma query, ele vai impedir que qualquer mudança que tenha ocorido seja de fato aplicada, 
	 * evitando erros
	 */
	@PostMapping
	@Transactional
	public ResponseEntity gerarCartao(HttpServletRequest request) {
		var idCliente = request.getAttribute("idCliente");
		
		try {
			var cartao = service.cadastrarCartao((String) idCliente);
			return ResponseEntity.ok(cartao);
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

	// Listando todos cartoes pelo id da conta
	@GetMapping
	public ResponseEntity consultaTodos(HttpServletRequest request) {
		var idCliente = request.getAttribute("idCliente");
		
		try {
			var cartoes = service.consultarCartoesDoCliente((String) idCliente);
			return ResponseEntity.ok(cartoes);
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
	
	// Deletando cartao pelo id
	@DeleteMapping("/{cartaoId}")
	@Transactional
	public ResponseEntity excluiCartao(@PathVariable String cartaoId) {
		try {
			service.excluirCartao(cartaoId);
			return ResponseEntity.noContent().build();
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