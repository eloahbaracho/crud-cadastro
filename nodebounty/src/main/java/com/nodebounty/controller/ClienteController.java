package com.nodebounty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodebounty.config.errors.ErroCustomizadoDTO;
import com.nodebounty.config.errors.RegistroNaoEncontradoException;
import com.nodebounty.config.security.DadosTokenJwt;
import com.nodebounty.config.security.TokenService;
import com.nodebounty.domain.cliente.Cliente;
import com.nodebounty.domain.cliente.DadosAtualizacaoCliente;
import com.nodebounty.domain.cliente.DadosAutenticacacao;
import com.nodebounty.domain.cliente.DadosCadastroCliente;
import com.nodebounty.service.IClienteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@SuppressWarnings("rawtypes")

/*
 * RequestMapping mostra por qual endpoint esse controller é responsável.
 * RestController define um controller
 */

public class ClienteController {
	@Autowired /* Injeção de dependência */
	private IClienteService service;
	
	@Autowired /* Injetando classe para realizar login e autenticação */
	private AuthenticationManager manager;
	
	@Autowired /* Injetando serviço de geração de token jwt para autenticação */
	private TokenService tokenService;
	
	private final String idclie = "idCliente";

	@GetMapping

	/*
	 * GetMapping Indica que esse método abaixo é responsável por cuidar das
	 * requisições que baterem no /Clientes e sejam do método get
	 * 
	 * Como o token JWT tem o id do cliente embutido, to recuperando esse id e usando ele para buscar
	 * e retornar os dados desse cliente em específico, pois nosso sistema não tem necessidade de mostrar
	 * os dados de TODOS os clientes cadastrados
	 */

	public ResponseEntity getCliente(HttpServletRequest request) {
		var idCliente = request.getAttribute(idclie);
		
		try {
			var cliente = service.consultarCliente((String) idCliente);
			return ResponseEntity.ok(cliente);
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

	@PostMapping
	@Transactional
	public ResponseEntity registerCliente(@RequestBody @Valid DadosCadastroCliente data) {
		try {
			service.cadastrarCliente(data);
			return ResponseEntity.ok().build();
		}
		catch (Exception e) {
			// Tratando qualquer outro tipo de erro, retornando um 400 - Bad request
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new ErroCustomizadoDTO(e.getMessage()));
		}
		/*
		 * RequestBody indica que ele quer buscar os valores mostrados através da
		 * requisição feita O valid vai fazer a validação do body que chegou via
		 * requisição pelo record requestclientes Exemplo: precisa vir um nome que é uma
		 * string e um endereço que também é uma string.
		 */

		/*
		 * o método ok() sem parâmetros é retornado um objeto do tipo BodyBuilder, mas o
		 * método ok(...) com parâmetros já devolve direto um objeto ResponseEntity.
		 */
	}

	@PutMapping /* putmapping é o método para atualizar */
	@Transactional
	public ResponseEntity updateCliente(@RequestBody @Valid DadosAtualizacaoCliente data, HttpServletRequest request) {
		var idCliente = request.getAttribute(idclie);
		
		try {
			var clienteAtualizado = service.atualizarCliente(data, (String) idCliente);
			return ResponseEntity.ok(clienteAtualizado);
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
	
	@DeleteMapping /* Método para deletar */
	@Transactional
	public ResponseEntity deleteCliente(HttpServletRequest request) {
		var idCliente = request.getAttribute(idclie);
		
		try {
			service.deletarCliente((String) idCliente);
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
	
	/* Post para realizar login */
	@PostMapping("/login")
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacacao data) {
		try {
			var token = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
			var authentication = manager.authenticate(token);
			
			var tokenJWT = tokenService.gerarToken((Cliente) authentication.getPrincipal());
			
			return ResponseEntity.ok(new DadosTokenJwt(tokenJWT));			
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErroCustomizadoDTO("E-mail ou senha inválidos"));
		}

	}
}
