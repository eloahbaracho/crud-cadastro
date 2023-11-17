package com.nodebounty.domain.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

/* Aqui se adiciona qual é a entidade e o tipo da chave primária dessa entidade */
public interface ClienteRepository extends JpaRepository<Cliente, String> {

	// Método necessário para autenticação
	UserDetails findByEmail(String email);

	boolean existsByRgOrCpfOrEmail(String rg, String cpf, String email);
	
}
