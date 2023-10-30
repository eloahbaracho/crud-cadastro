package com.nodebounty.service;

import com.nodebounty.domain.cliente.Cliente;
import com.nodebounty.domain.cliente.DadosAtualizacaoCliente;
import com.nodebounty.domain.cliente.DadosCadastroCliente;

public interface IClienteService {

	public void cadastrarCliente(DadosCadastroCliente data) throws Exception;
	
	public Cliente consultarCliente(String idCliente);
	
	public Cliente atualizarCliente(DadosAtualizacaoCliente data, String idCliente);
	
	public void deletarCliente(String idCliente);

}
