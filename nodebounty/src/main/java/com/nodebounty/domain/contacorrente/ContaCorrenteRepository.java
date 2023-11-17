package com.nodebounty.domain.contacorrente;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nodebounty.domain.cliente.Cliente;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, String> {

	ContaCorrente findByCliente(Cliente cliente);

	boolean existsByCliente(Cliente cliente);

	ContaCorrente findByNumeroConta(String numeroConta);
	
}