package com.nodebounty.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodebounty.config.security.TokenService;
import com.nodebounty.domain.cliente.Cliente;
import com.nodebounty.domain.cliente.ClienteRepository;
import com.nodebounty.domain.cliente.DadosAtualizacaoCliente;
import com.nodebounty.domain.cliente.DadosAutenticacacao;
import com.nodebounty.domain.cliente.DadosCadastroCliente;
import com.nodebounty.config.security.DadosTokenJwt;

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
	private ClienteRepository repository;
	
	@Autowired /* Injetando classe para realizar login e autenticação */
	private AuthenticationManager manager;
	
	@Autowired /* Injetando serviço de geração de token jwt para autenticação */
	private TokenService tokenService;
	
	@Autowired /* Injetando classe para criptografar senha, no padrão que o springsecurity exige */
	private PasswordEncoder passwordEncoder;

	@GetMapping

	/*
	 * GetMapping Indica que esse método abaixo é responsável por cuidar das
	 * requisições que baterem no /Clientes e sejam do método get
	 */

	public ResponseEntity GetAllClientes() {
		var allClientes = repository.findAll();
		return ResponseEntity.ok(allClientes);
		/*
		 * ResponseEntity é o tipo de classe utilizada para a manipulação de dados
		 * retornados
		 */
	}

	@PostMapping
	@Transactional
	public ResponseEntity registerClientes(@RequestBody @Valid DadosCadastroCliente data) {
		Cliente newCliente = new Cliente(data);
		newCliente.setSenha(passwordEncoder.encode(data.senha())); /* Criptografando senha */
		repository.save(newCliente);

		System.out.print(data);
		return ResponseEntity.ok().build();
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
	public ResponseEntity updateCliente(@RequestBody @Valid DadosAtualizacaoCliente data) {
		Optional<Cliente> optionalCliente = repository.findById(data.idCliente());

		if (optionalCliente.isPresent()) {
			Cliente cliente = optionalCliente.get();
			cliente.atualizarDados(data);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteCliente(@PathVariable Long id) {
   		 Optional<Cliente> optionalCliente = repository.findById(productId);
    			if (optionalCliente.isPresent()) {
        		Cliente cliente = optionalProduct.get();
        		repository.delete(cliente);
        		return ResponseEntity.ok().build();
   		 } else {
        	return ResponseEntity.notFound().build();
    }
}

	
	
	/* Post para realizar login */
	@PostMapping("/login")
	public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacacao data) {
		var token = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
		var authentication = manager.authenticate(token);
		
		var tokenJWT = tokenService.gerarToken((Cliente) authentication.getPrincipal());
		
		return ResponseEntity.ok(new DadosTokenJwt(tokenJWT));
	}
}
