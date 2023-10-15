package com.example.cadastro.domain;


import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/* NotBlank não permite valores null e nem valores vazios como: '' *
/* Como o ID é um valor que o back-end vai gerar, não precisa nem inserir nos dados de cadastro, mesmo que seja opcional */


public record DadosCadastroCliente(
	@NotBlank(message="Esse campo é obrigatório")	 String Nome, 
	@NotBlank(message="Esse campo é obrigatório")	 String Endereco, 
	@NotBlank(message="Esse campo é obrigatório")	 String Cep,
	@NotNull(message="Esse campo é obrigatório")	 Integer Numero,
	@NotBlank(message="Esse campo é obrigatório")	 String Rg,
	@NotBlank(message="Esse campo é obrigatório")	 String Cpf,
	@NotBlank(message="Esse campo é obrigatório")	 String DataNascimento,
	@NotBlank(message="Esse campo é obrigatório") @Email(message="E-mail inválido")	 String Email,
	@NotBlank(message="Esse campo é obrigatório")	 String Senha
){

}

