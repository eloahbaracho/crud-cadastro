package com.nodebounty.service;

import java.util.List;

import com.nodebounty.domain.cartao.Cartao;

public interface ICartaoService {

	public Cartao cadastrarCartao(String idCliente);
	
	public List<Cartao> consultarCartoesDoCliente(String idCliente);
	
	public void excluirCartao(String idCartao);
	
}
