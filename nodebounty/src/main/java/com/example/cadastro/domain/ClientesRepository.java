package com.example.cadastro.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Clientes, String> {

	/* Aqui se adiciona qual é a entidade e o tipo da chave primária dessa entidade */
}
