package com.nodebounty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nodebounty.config.errors.RegistroNaoEncontradoException;
import com.nodebounty.domain.cliente.Cliente;
import com.nodebounty.domain.cliente.ClienteRepository;
import com.nodebounty.domain.cliente.DadosAtualizacaoCliente;
import com.nodebounty.domain.cliente.DadosCadastroCliente;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired /* Injetando classe para criptografar senha, no padrão que o springsecurity exige */
	private PasswordEncoder passwordEncoder;
	
	private final String clientenaoenc = "cliente não encontrado";
	
	// Método para cadastrar o cliente
	public void cadastrarCliente(DadosCadastroCliente data) throws Exception {
		// Validando atributos que devem ser únicos, como: RG, CPF e Email
		var clienteJaExiste = clienteRepository.existsByRgOrCpfOrEmail(data.rg(), data.cpf(), data.email());
		
		if (clienteJaExiste) {
			throw new Exception("Os dados fornecidos já existem no sistema");
		}
		
		// Validações bem sucedidas, criando e persistindo o cliente
		var cliente = new Cliente(data);
		cliente.setSenha(passwordEncoder.encode(data.senha())); /* Criptografando senha */
		clienteRepository.save(cliente);
	}
	
	// Método para listar os dados de um cliente pelo id
	public Cliente consultarCliente(String idCliente) {
    	var cliente = clienteRepository.findById(idCliente);
    	
    	// Verificando se o cliente existe
    	if (!cliente.isPresent()) {
    		throw new RegistroNaoEncontradoException(clientenaoenc);
    	}
    	
    	// Retornando os dados do cliente
    	return cliente.get();
	}
	
	// Método para atualizar o cliente
	public Cliente atualizarCliente(DadosAtualizacaoCliente data, String idCliente) {
    	var cliente = clienteRepository.findById(idCliente);
    	
    	// Verificando se o cliente existe
    	if (!cliente.isPresent()) {
    		throw new RegistroNaoEncontradoException(clientenaoenc);
    	}
    	
    	// Verificando se a senha foi modificada. Caso tenha sido, gerar sua versão criptografada e atualizar o cliente
    	if (data.senha() != null) {
    		var senhaCriptografada = passwordEncoder.encode(data.senha());
    		cliente.get().atualizarDados(data, senhaCriptografada);
    	}
    	else {
	    	// Atualizando o cliente quando a senha não foi modificada
	    	cliente.get().atualizarDados(data, null);    		
    	}
    	
    	// Retornando o cliente atualizado para o front-end não precisar dar f5
    	return cliente.get();
	}
	
	// Método para deletar o cliente
	public void deletarCliente(String idCliente) {
    	var clienteExisteNoSistema = clienteRepository.existsById(idCliente);
    	
    	// Retornando erro caso o id do cliente não exista no banco
    	if (!clienteExisteNoSistema) {
    		throw new RegistroNaoEncontradoException(clientenaoenc);
    	}
    	
    	// Excluindo o cliente
    	clienteRepository.deleteById(idCliente);
	}
	
}
