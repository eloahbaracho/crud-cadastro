package com.nodebounty.service;

import com.nodebounty.domain.contacorrente.ContaCorrente;
import com.nodebounty.domain.contacorrente.DadosPlanoSelecionado;

public interface IContaCorrenteService {

	public void cadastrarContaCorrente(DadosPlanoSelecionado data, String idCliente) throws Exception;
	
	public ContaCorrente listarDadosDaContaPeloIdDoCliente(String idCliente) throws Exception;
	
}
