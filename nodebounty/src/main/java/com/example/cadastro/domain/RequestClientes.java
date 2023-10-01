package com.example.cadastro.domain;

import jakarta.validation.constraints.NotNull;

public record RequestClientes(
				 String id,
	@NotNull	 String Nome, 
	@NotNull	 String Endereco, 
	@NotNull	 String Cep,
	@NotNull	 Integer Numero,
	@NotNull	 String Rg,
	@NotNull	 String CPF,
	@NotNull	 String Email,
	@NotNull	 String DataNascimento
		){

}

